package woowacourse.omok

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import domain.domain.CoordinateState
import domain.domain.OmokGame
import domain.domain.Position
import domain.domain.ProgressState
import domain.library.combinerule.CombinedRuleAdapter
import woowacourse.omok.util.setOnSingleClickListener
import kotlin.properties.Delegates

class GameActivity : AppCompatActivity() {

    private val omokGame: OmokGame = OmokGame(gameRule = CombinedRuleAdapter())

    private lateinit var turnView: TurnView
    private lateinit var lastPositionView: LastPositionView
    private lateinit var tvMessage: TextView

    private var message: String by Delegates.observable("") { _, _, new ->
        tvMessage.text = new
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val board = findViewById<TableLayout>(R.id.board)
        setBoardOnclickListener(board)
        initViewId()
        initGameViewState()
    }

    private fun initViewId() {
        turnView = TurnView(findViewById(R.id.tv_turn), omokGame)
        lastPositionView = LastPositionView(findViewById(R.id.tv_last_position), omokGame)
        tvMessage = findViewById(R.id.tv_message)
    }

    private fun initGameViewState() {
        tvMessage.text = getText(R.string.start_message)
    }

    private fun setBoardOnclickListener(board: TableLayout) {
        board
            .children
            .filterIsInstance<TableRow>()
            .forEachIndexed { rowIndex, tableRow ->
                setCoordinateOnclickListener(
                    tableRow,
                    rowIndex
                )
            }
    }

    private fun setCoordinateOnclickListener(
        tableRow: TableRow,
        rowIndex: Int
    ) = tableRow.children
        .filterIsInstance<ImageView>()
        .forEachIndexed() { columIndex, imageView ->
            imageView.setOnSingleClickListener {
                coordinateClickListener(
                    imageView = imageView,
                    rowIndex = rowIndex,
                    columIndex = columIndex
                )
            }
        }

    private fun coordinateClickListener(imageView: ImageView, rowIndex: Int, columIndex: Int) {
        val turnStoneColor = omokGame.turn
        if (progressGame(
                Position(rowIndex, columIndex)
            )
        ) {
            if (turnStoneColor == CoordinateState.BLACK) {
                imageView.setImageResource(R.drawable.black_stone)
                turnView.turn = omokGame.turn
                lastPositionView.lastPosition = omokGame.board.lastPosition
            }
            if (turnStoneColor == CoordinateState.WHITE) {
                imageView.setImageResource(R.drawable.white_stone)
                turnView.turn = omokGame.turn
                lastPositionView.lastPosition = omokGame.board.lastPosition
            }
        }
    }

    private fun printError() {
        message = getString(R.string.turn_error_message)
    }

    private fun printRequestPosition() {
        message = getString(R.string.request_position_message)
    }

    private fun finishGame(winner: CoordinateState) {
        val intent = Intent(this, FinishActivity::class.java).apply {
            putExtra("winner", winner)
            putExtra("board", omokGame.board.boardState)
        }
        startActivity(intent)
        finish()
    }

    private fun progressGame(position: Position): Boolean =
        when (omokGame.progressTurn(position)) {
            ProgressState.ERROR -> {
                printError()
                false
            }
            ProgressState.END -> {
                finishGame(omokGame.turn)
                true
            }
            ProgressState.CONTINUE -> {
                printRequestPosition()
                true
            }
        }
}
