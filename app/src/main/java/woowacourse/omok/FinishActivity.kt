package woowacourse.omok

import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import domain.domain.BoardState
import domain.domain.CoordinateState
import woowacourse.omok.util.customGetSerializable

class FinishActivity : AppCompatActivity() {

    private lateinit var winner: CoordinateState
    private lateinit var board: BoardState
    private lateinit var tvWinnerMessage: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)

        initExtraData()
        initViewId()
        initWinnerMessage()
        setBoardInitState(findViewById(R.id.finish_board))
    }

    private fun initExtraData() {
        winner = intent.customGetSerializable("winner")
            ?: throw IllegalStateException()
        board = intent.customGetSerializable("board")
            ?: throw IllegalStateException()
    }

    private fun initViewId() {
        tvWinnerMessage = findViewById(R.id.tv_winner_message)
    }

    private fun initWinnerMessage() {
        tvWinnerMessage.text = "${winner.name}의 승리"
    }

    private fun setBoardInitState(board: TableLayout) {
        board
            .children
            .filterIsInstance<TableRow>()
            .forEachIndexed { rowIndex, tableRow ->
                setCoordinateInitState(
                    tableRow,
                    rowIndex
                )
            }
    }

    private fun setCoordinateInitState(
        tableRow: TableRow,
        rowIndex: Int
    ) = tableRow.children
        .filterIsInstance<ImageView>()
        .forEachIndexed() { columIndex, imageView ->
            initBard(rowIndex, columIndex, imageView)
        }

    private fun initBard(
        rowIndex: Int,
        columIndex: Int,
        imageView: ImageView
    ) {
        board.value.forEachIndexed { yIndex, rowList ->
            rowList.forEachIndexed { xIndex, coordinateState ->
                if (rowIndex == yIndex && columIndex == xIndex) {
                    if (coordinateState == CoordinateState.BLACK) imageView.setImageResource(R.drawable.black_stone)
                    if (coordinateState == CoordinateState.WHITE) imageView.setImageResource(R.drawable.white_stone)
                }
            }
        }
    }
}
