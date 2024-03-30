package woowacourse.omok.view

import android.widget.ImageView
import woowacourse.omok.model.Board
import woowacourse.omok.model.OmokStone
import woowacourse.omok.util.convertPositionToIndex
import woowacourse.omok.util.mapStoneColorToDrawable

class BoardView(val spaceViews: List<ImageView>) : OmokView {
    override fun updateBoard(board: Board) {
        board.stones.entries.forEach {
            updateSingleSpace(it.value)
        }
    }

    override fun updateSingleSpace(omokStone: OmokStone) {
        val index = convertPositionToIndex(omokStone.position)
        val targetColor = mapStoneColorToDrawable(omokStone.color)
        spaceViews[index].setImageResource(targetColor)
    }

    fun resetView() {
        spaceViews.forEach {
            it.setImageResource(android.R.color.transparent)
        }
    }
}
