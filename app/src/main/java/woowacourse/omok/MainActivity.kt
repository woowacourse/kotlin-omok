package woowacourse.omok

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import domain.OmokGame
import domain.stone.Color
import domain.stone.Column
import domain.stone.Position
import domain.stone.Row
import domain.stone.Stone
import woowacourse.omok.db.StonesHelper

class MainActivity : AppCompatActivity() {
    private lateinit var omokGame: OmokGame
    private lateinit var stonesHelper: StonesHelper
    private var roomId: Int = 0 // 추후 업데이트를 위한 게임방 아이디에 대한 프로터티. 방 목록 액티비티에서 받을 것임.
    private lateinit var board: TableLayout
    private lateinit var gameEndBox: LinearLayout
    private lateinit var gameEndButton: Button
    private lateinit var gameRetryButton: Button
    private lateinit var turnColorTextView: TextView
    private lateinit var turnColorTextBox: LinearLayout
    private val originBoardDrawableSet: List<Drawable> by lazy { getAllOmokPositionImageView().map { it.drawable } }
    private val backKeyHandler = DoubleClickBackKeyHandler()
    private val vibrationService: Vibrator by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibrationServiceManager =
                getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibrationServiceManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }
    }
    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            backKeyHandler.onBackPressed(
                { Toaster.showToast(baseContext, R.string.one_more_click_please_back_key) },
                ::endButtonOnClick
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initFindViewById()
        setClickListener()
        stonesHelper = StonesHelper(this, roomId)
        this.onBackPressedDispatcher.addCallback(this, callback)
        startGame()
    }

    private fun getAllOmokPositionImageView(): List<ImageView> {
        return board
            .children
            .filterIsInstance<TableRow>()
            .flatMap { it.children }
            .filterIsInstance<ImageView>()
            .toList()
    }

    private fun getOmokImageViewFromPosition(position: Position): ImageView {
        val index = (ROW_SIZE - 1 - position.row.ordinal) * COLUMN_SIZE + position.column.ordinal
        Log.d("mendel", "column: ${position.column} , row: ${position.row} , index: $index")
        return getAllOmokPositionImageView()[index]
    }

    private fun initFindViewById() {
        turnColorTextBox = findViewById(R.id.turn_color_text_box)
        turnColorTextView = findViewById(R.id.turn_color)
        gameEndBox = findViewById(R.id.game_end_box)
        gameEndButton = findViewById(R.id.game_end_button)
        gameRetryButton = findViewById(R.id.game_retry)
        board = findViewById(R.id.board)
    }

    private fun setClickListener() {
        gameEndButton.setOnClickListener { endButtonOnClick() }
        gameRetryButton.setOnClickListener { startGame() }
        getAllOmokPositionImageView().forEachIndexed { index, view ->
            view.setOnClickListener {
                val position = convertIndexToPosition(index)
                omokGame.playTurn(position, ::putStoneProcess, ::putFailedProcess)
            }
        }
    }

    private fun startGame() {
        omokGame = OmokGame(::turnTextUpdate)
        val loadData = stonesHelper.getAlreadyPutStones()
        loadData.forEach {
            omokGame.playTurn(it.position) // 게임 복기
        }
        turnTextUpdate() // 현재 턴 텍스트뷰 설정
        setBoardView() // 뷰 되돌리기
        gameStartProcess() // 전처리 수행
    }

    private fun setBoardView() {
        getAllOmokPositionImageView().forEachIndexed { index, view ->
            val position = convertIndexToPosition(index)
            when (val color = omokGame.board[position]) {
                null -> view.setImageDrawable(originBoardDrawableSet[index])
                else -> setStone(Stone(position, color), view)
            }
        }
    }

    private fun putStoneProcess(omokGame: OmokGame) {
        val newStone = omokGame.latestStone ?: return
        vibrationService.vibrate(VibrationEffect.createOneShot(20, 50))
        stonesHelper.insertStone(newStone)
        setStone(newStone, getOmokImageViewFromPosition(newStone.position))
        if (omokGame.isFinished) gameFinishProcess(omokGame)
    }

    private fun putFailedProcess() =
        if (omokGame.isFinished.not()) {
            makeToastMessage(R.string.can_not_put_this_position)
        } else {
            makeToastMessage(R.string.already_game_is_over)
        }

    private fun gameFinishProcess(omokGame: OmokGame) {
        makeToastMessage(R.string.game_is_over)
        val winner =
            omokGame.winnerColor?.let { stoneColorKorean(it) } ?: getString(R.string.no_winner)
        gameEndButton.text = getString(R.string.winner_is_this_color, winner)
        gameEndBox.visibility = View.VISIBLE
        turnColorTextBox.visibility = View.GONE
        stonesHelper.deleteAll()
    }

    private fun convertIndexToPosition(index: Int): Position {
        val row = ROW_SIZE - 1 - (index / ROW_SIZE)
        val column = index % COLUMN_SIZE
        Log.d("mendel", "index: $index , column: $column , row: $row")
        return Position(column + 1, row + 1)
    }

    private fun setStone(stone: Stone, view: ImageView) =
        when (stone.color) {
            Color.BLACK -> view.setImageResource(R.drawable.black_navi_stone)
            Color.WHITE -> view.setImageResource(R.drawable.white_choonbae_stone)
        }

    private fun gameStartProcess() {
        gameEndBox.visibility = View.GONE
        turnColorTextBox.visibility = View.VISIBLE
    }

    private fun turnTextUpdate() {
        turnColorTextView.text = stoneColorKorean(omokGame.turnColor)
    }

    override fun onDestroy() {
        super.onDestroy()
        stonesHelper.close()
    }

    private fun endButtonOnClick() =
        if (omokGame.isFinished.not()) {
            showAskDialog(R.string.exit_game, R.string.progressing_game_will_be_save, { finish() })
        } else {
            showAskDialog(R.string.exit_game, R.string.exit_game_room_confirm_message, { finish() })
        }

    private fun stoneColorKorean(color: Color): String =
        when (color) {
            Color.BLACK -> BLACK
            Color.WHITE -> WHITE
        }

    companion object {
        private val COLUMN_SIZE = Column.values().size
        private val ROW_SIZE = Row.values().size
        private const val BLACK = "흑"
        private const val WHITE = "백"
    }
}
