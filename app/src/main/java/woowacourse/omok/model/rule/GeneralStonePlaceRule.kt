package omok.model.rule

import omok.model.Board
import omok.model.OmokStone

object GeneralStonePlaceRule : StonePlaceRule() {
    override fun canPlace(
        stone: OmokStone,
        board: Board,
    ): Boolean = super.canPlace(stone, board)
}
