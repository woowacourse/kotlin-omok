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
import woowacourse.omok.db.StoneTableAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var omokGame: OmokGame
    private lateinit var stoneTableAdapter: StoneTableAdapter
    private var roomId: Int = 0 // 추후 업데이트를 위한 게임방 아이디에 대한 프로터티. 방 목록 액티비티에서 받을 것임.
    private lateinit var board: TableLayout
    private lateinit var gameEndBox: LinearLayout
    private lateinit var gameEndButton: Button
    private lateinit var gameRetryButton: Button
    private lateinit var turnColorTextView: TextView
    private lateinit var turnColorTextBox: LinearLayout
    private val originBoardDrawableSet: List<Drawable> by lazy { getAllOmokPositionImageView().map { it.drawable } }
    private val backKeyHandler = BackKeyHandler(this)
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
            backKeyHandler.onBackPressed(::endButtonOnClick)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initFindViewById()
        setClickListener()
        stoneTableAdapter = StoneTableAdapter(this, roomId)
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
                positionClick(convertIndexToPosition(index), view)
            }
        }
    }

    private fun startGame() {
        omokGame = OmokGame()
        val loadData = stoneTableAdapter.getAlreadyPutStones()
        loadData.forEach {
            omokGame.playTurn(it.position) // 게임 복기
        }
        turnColorTextView.text = omokGame.turnColor.korean
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

    private fun positionClick(position: Position, view: ImageView) {
        val playTurnColor = omokGame.playTurn(position)
        when {
            playTurnColor == null && omokGame.isFinished.not() -> makeToastMessage(R.string.can_not_put_this_position)
            playTurnColor == null -> makeToastMessage(R.string.already_game_is_over)
            else -> with(Stone(position, playTurnColor)) {
                putStoneProcess(this)
                setStone(this, view)
            }
        }
        if (omokGame.isFinished) gameFinishProcess()
    }

    private fun convertIndexToPosition(index: Int): Position {
        val row = ROW_SIZE - 1 - (index / ROW_SIZE)
        val column = index % COLUMN_SIZE
        Log.d("mendel", "index: $index , column: $column , row: $row")
        return Position(column + 1, row + 1)
    }

    private fun putStoneProcess(stone: Stone) {
        vibrationService.vibrate(VibrationEffect.createOneShot(20, 50))
        stoneTableAdapter.insertStone(stone)
        turnColorTextView.text = omokGame.turnColor.korean
    }

    private fun setStone(stone: Stone, view: ImageView) =
        when (stone.color) {
            Color.BLACK -> view.setImageResource(R.drawable.black_navi_stone)
            Color.WHITE -> view.setImageResource(R.drawable.white_choonbae_stone)
        }

    private fun gameStartProcess() {
        gameEndBox.visibility = View.GONE
        turnColorTextBox.visibility = View.VISIBLE
        turnColorTextView.text = omokGame.turnColor.korean
    }

    private fun gameFinishProcess() {
        makeToastMessage(R.string.game_is_over)
        gameEndButton.text =
            getString(R.string.winner_is_this_color, omokGame.winnerColor?.korean)
        gameEndBox.visibility = View.VISIBLE
        turnColorTextBox.visibility = View.GONE
        stoneTableAdapter.deleteAll()
    }

    override fun onDestroy() {
        super.onDestroy()
        stoneTableAdapter.close()
    }

    private fun endButtonOnClick() =
        if (omokGame.isFinished.not()) {
            showAskDialog(R.string.exit_game, R.string.progressing_game_will_be_save, { finish() })
        } else {
            showAskDialog(R.string.exit_game, R.string.exit_game_room_confirm_message, { finish() })
        }

    companion object {
        private val COLUMN_SIZE = Column.values().size
        private val ROW_SIZE = Row.values().size
    }
}
