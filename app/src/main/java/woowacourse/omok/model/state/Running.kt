package omok.model.state

import omok.model.Board
import omok.model.OmokStone
import omok.model.rule.StonePlaceRule

sealed class Running(private val stonePlaceRule: StonePlaceRule, board: Board) : GameState(board) {
    protected fun canPut(stone: OmokStone): Boolean {
        return stonePlaceRule.canPlace(stone, board)
    }
}
