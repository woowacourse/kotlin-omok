package woowacourse.omok.view

import android.widget.ImageView
import woowacourse.omok.model.Board
import woowacourse.omok.model.Position
import woowacourse.omok.util.mapStoneColorToDrawable

class OmokBoardView(private val view: ImageView) :
    OmokView {
    override fun updateBoard(
        position: Position,
        board: Board,
    ) {
        board[position]?.run {
            val targetColor = mapStoneColorToDrawable(this.color)
            view.setImageResource(targetColor)
        }
    }
}
