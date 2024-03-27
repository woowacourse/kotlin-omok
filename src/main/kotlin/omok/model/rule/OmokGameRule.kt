package omok.model.rule

import omok.model.OmokStone
import omok.model.board.Board

fun interface OmokGameRule {
    fun canPlaceStone(
        stone: OmokStone,
        board: Board,
    ): Boolean
}
