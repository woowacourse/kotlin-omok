package omok.domain.model.rule

import omok.domain.model.Board
import omok.domain.model.stone.OmokStone

fun interface OmokRule {
    fun canPlace(
        omokStone: OmokStone,
        board: Board,
    ): Boolean
}
