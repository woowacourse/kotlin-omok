package woowacourse.omok

import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import domain.domain.Board
import domain.domain.BoardState
import domain.domain.CoordinateState

class FinishActivity : AppCompatActivity() {

    private lateinit var winner: CoordinateState
    private lateinit var board: BoardState
    private lateinit var tvWinnerMessage: TextView

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)

        initExtraData()
        initViewId()
        initWinnerMessage()
        setBoardInitState(findViewById(R.id.finish_board))
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun initExtraData() {
        winner = intent.getParcelableExtra("winner", CoordinateState::class.java)
            ?: throw IllegalStateException()
        board = intent.getSerializableExtra("board", BoardState::class.java)
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
