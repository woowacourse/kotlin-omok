package woowacourse.omok.view

import android.widget.ImageView
import omok.model.Position
import omok.model.state.GameState
import woowacourse.omok.util.mapStoneColorToDrawable

class OmokBoardView(private val view: ImageView) :
    OmokView {
    override fun updateBoard(
        position: Position,
        gameState: GameState,
    ) {
        gameState.board[position]?.run {
            val targetColor = mapStoneColorToDrawable(this.color)
            view.setImageResource(targetColor)
        }
    }
}
