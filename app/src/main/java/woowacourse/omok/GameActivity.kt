package woowacourse.omok

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

    init {
        OmokApplication.controller.run()
    }

    lateinit var tvTurn: TextView
    lateinit var tvLastPosition: TextView
    var turn by Delegates.observable(OmokApplication.controller.omokGame.turn) { _, _, new ->
        tvTurn.text = new.name
    }
    var lastPosition: Position? by Delegates.observable(OmokApplication.controller.omokGame.board.lastPosition) { _, _, new ->
        if (new == null) {
            tvLastPosition.text = ""
        } else {
            tvLastPosition.text =
                "${AlphabetCoordinate.convertAlphabet(new.coordinateX)}${Board.BOARD_SIZE - new.coordinateY}"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val board = findViewById<TableLayout>(R.id.board)
        setBoardOnclickListener(board)
        initGameStateView()
    }

    private fun initGameStateView() {
        tvTurn = findViewById(R.id.tv_turn)
        tvLastPosition = findViewById(R.id.tv_last_position)
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
        val turnStoneColor = OmokApplication.controller.omokGame.turn
        if (OmokApplication.controller.progressGame(
                Position(rowIndex, columIndex),
                ::printError,
                ::printRequestPosition,
                ::finishGame
            )
        ) {
            if (turnStoneColor == CoordinateState.BLACK) {
                imageView.setImageResource(R.drawable.black_stone)
                turn = OmokApplication.controller.omokGame.turn
                lastPosition = OmokApplication.controller.omokGame.board.lastPosition
            }
            if (turnStoneColor == CoordinateState.WHITE) {
                imageView.setImageResource(R.drawable.white_stone)
                turn = OmokApplication.controller.omokGame.turn
                lastPosition = OmokApplication.controller.omokGame.board.lastPosition
            }
        }
    }

    private fun printError() {
        Toast.makeText(this, TURN_ERROR_MESSAGE, Toast.LENGTH_SHORT).show()
    }

    private fun printRequestPosition() {
        Toast.makeText(this, REQUEST_POSITION_MESSAGE, Toast.LENGTH_SHORT).show()
    }

    private fun finishGame() {
        Toast.makeText(this, "액티비티로 바꿀꺼임", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        OmokApplication.controller = AndroidController()
        super.onDestroy()
    }

    companion object {
        private const val TURN_ERROR_MESSAGE = "금수 혹은 이미 놓은 자리입니다 다시 시도해주세요"
        private const val REQUEST_POSITION_MESSAGE = "위치를 터치해 주세요"
    }
}
