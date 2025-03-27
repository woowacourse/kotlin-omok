package omok.domain.model.rule

import omok.domain.model.Board
import omok.domain.model.stone.OmokStone

interface OmokRule {
    fun checkWin(
        omokStone: OmokStone,
        board: Board,
    ): Boolean

    fun checkAnyFoulCondition(
        omokStone: OmokStone,
        board: Board,
    ): Boolean
}
