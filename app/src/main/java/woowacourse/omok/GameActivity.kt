package woowacourse.omok

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import domain.domain.Board
import domain.domain.CoordinateState
import domain.domain.Position
import domain.view.AlphabetCoordinate
import woowacourse.omok.util.setOnSingleClickListener
import kotlin.properties.Delegates

class GameActivity : AppCompatActivity() {

    private var controller: OmokGameWrapper = OmokGameWrapper()

    private lateinit var tvTurn: TextView
    private lateinit var tvLastPosition: TextView
    private lateinit var tvMessage: TextView

    private var turn by Delegates.observable(controller.omokGame.turn) { _, _, new ->
        tvTurn.text = new.name
    }
    private var lastPosition: Position? by Delegates.observable(controller.omokGame.board.lastPosition) { _, _, new ->
        if (new == null) {
            tvLastPosition.text = ""
        } else {
            tvLastPosition.text =
                "${AlphabetCoordinate.convertAlphabet(new.coordinateX)}${Board.BOARD_SIZE - new.coordinateY}"
        }
    }
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
        tvTurn = findViewById(R.id.tv_turn)
        tvLastPosition = findViewById(R.id.tv_last_position)
        tvMessage = findViewById(R.id.tv_message)
    }

    private fun initGameViewState() {
        tvTurn.text = turn.name
        lastPosition?.let {
            tvLastPosition.text =
                "${AlphabetCoordinate.convertAlphabet(it.coordinateX)}${Board.BOARD_SIZE - it.coordinateY}"
        }
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
        val turnStoneColor = controller.omokGame.turn
        if (controller.progressGame(
                Position(rowIndex, columIndex),
                ::printError,
                ::printRequestPosition,
                ::finishGame
            )
        ) {
            if (turnStoneColor == CoordinateState.BLACK) {
                imageView.setImageResource(R.drawable.black_stone)
                turn = controller.omokGame.turn
                lastPosition = controller.omokGame.board.lastPosition
            }
            if (turnStoneColor == CoordinateState.WHITE) {
                imageView.setImageResource(R.drawable.white_stone)
                turn = controller.omokGame.turn
                lastPosition = controller.omokGame.board.lastPosition
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
            putExtra("board", controller.omokGame.board.boardState)
        }
        startActivity(intent)
        finish()
    }
}
