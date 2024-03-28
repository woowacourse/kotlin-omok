package woowacourse.omok.model.rule

import woowacourse.omok.model.Board
import woowacourse.omok.model.OmokStone

abstract class StonePlaceRule {
    open fun canPlace(
        stone: OmokStone,
        board: Board,
    ): Boolean {
        val isEmptyPosition = board.isEmptyPosition(stone)
        val isInRange = board.isInRange(stone)
        return isEmptyPosition && isInRange
    }
}
