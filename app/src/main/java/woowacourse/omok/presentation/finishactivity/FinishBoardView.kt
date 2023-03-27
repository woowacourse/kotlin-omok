package woowacourse.omok.presentation.finishactivity

import android.widget.ImageView
import android.widget.TableLayout
import domain.domain.BoardState
import domain.domain.CoordinateState
import woowacourse.omok.R
import woowacourse.omok.presentation.BoardView

class FinishBoardView(
    value: TableLayout,
    private val board: BoardState
) : BoardView(value) {
    override fun boardTask(imageView: ImageView, rowIndex: Int, columIndex: Int) {
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
