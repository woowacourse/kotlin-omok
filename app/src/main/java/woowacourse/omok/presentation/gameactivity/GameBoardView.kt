package woowacourse.omok.presentation.gameactivity

import android.widget.ImageView
import android.widget.TableLayout
import domain.domain.BoardState
import domain.domain.CoordinateState
import woowacourse.omok.R
import woowacourse.omok.presentation.BoardView
import woowacourse.omok.util.setOnSingleClickListener

class GameBoardView(
    value: TableLayout,
    private val board: BoardState,
    private val coordinateClickListener: (ImageView, Int, Int) -> Unit
) : BoardView(value) {

    init {
        setBoardTask()
    }

    override fun boardTask(imageView: ImageView, rowIndex: Int, columIndex: Int) {
        imageView.setOnSingleClickListener {
            coordinateClickListener(imageView, rowIndex, columIndex)
        }
        initBoardView(imageView, rowIndex, columIndex)
    }

    private fun initBoardView(imageView: ImageView, rowIndex: Int, columIndex: Int) {
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
