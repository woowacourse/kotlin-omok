package woowacourse.omok.model.rule

import woowacourse.omok.model.Board
import woowacourse.omok.model.OmokStone

object GeneralStonePlaceRule : StonePlaceRule() {
    override fun canPlace(
        stone: OmokStone,
        board: Board,
    ): Boolean = super.canPlace(stone, board)
}
