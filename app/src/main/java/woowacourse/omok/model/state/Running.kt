package woowacourse.omok.model.state

import woowacourse.omok.model.Board
import woowacourse.omok.model.OmokStone
import woowacourse.omok.model.rule.StonePlaceRule

sealed class Running(private val stonePlaceRule: StonePlaceRule, board: Board) : GameState(board) {
    protected fun canPut(stone: OmokStone): Boolean {
        return stonePlaceRule.canPlace(stone, board)
    }
}
