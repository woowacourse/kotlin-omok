package woowacourse.omok.model.state

import woowacourse.omok.model.omok.Board
import woowacourse.omok.model.omok.OmokStone
import woowacourse.omok.model.rule.StonePlaceRule

sealed class Running(private val stonePlaceRule: StonePlaceRule, board: Board) : GameState(board) {
    protected fun canPut(stone: OmokStone): Boolean {
        return stonePlaceRule.canPlace(stone, board)
    }
}
