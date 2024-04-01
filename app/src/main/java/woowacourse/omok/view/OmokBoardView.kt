package woowacourse.omok.view

import android.widget.ImageView
import woowacourse.omok.R
import woowacourse.omok.model.Board
import woowacourse.omok.model.OmokStone
import woowacourse.omok.model.Position
import woowacourse.omok.model.StoneColor

class OmokBoardView(val spaceViews: List<ImageView>) : OmokView {
    fun setupClickListener(onPlace: (Position) -> Unit) {
        spaceViews.forEachIndexed { index, spaceView ->
            spaceView.setOnClickListener {
                val position = convertIndexToPosition(index)
                onPlace(position)
            }
        }
    }

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

    private fun mapStoneColorToDrawable(color: StoneColor): Int {
        return when (color) {
            StoneColor.BLACK -> R.drawable.black_stone
            StoneColor.WHITE -> R.drawable.white_stone
        }
    }

    private fun convertIndexToPosition(index: Int): Position {
        val x = index % 15 + 1
        val y = (224 - index) / 15 + 1
        return Position.of(x, y)
    }

    private fun convertPositionToIndex(position: Position): Int {
        val x = position.x - 1
        val y = 14 - (position.y - 1)
        return y * 15 + x
    }
}
