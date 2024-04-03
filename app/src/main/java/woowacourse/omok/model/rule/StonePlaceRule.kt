package woowacourse.omok.model.rule

import woowacourse.omok.model.Board
import woowacourse.omok.model.OmokStone

open class StonePlaceRule {
    open fun canPlace(
        stone: OmokStone,
        board: Board,
    ): Boolean {
        val isEmptyPosition = board.isEmptyPosition(stone.position)
        val isInRange = board.isInRange(stone.position)
        return isEmptyPosition && isInRange
    }
}
