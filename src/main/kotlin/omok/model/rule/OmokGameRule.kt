package omok.model.rule

import omok.model.Board
import omok.model.OmokStone

fun interface OmokGameRule {
    fun canPlaceStone(
        stone: OmokStone,
        board: Board,
    ): Boolean
}
