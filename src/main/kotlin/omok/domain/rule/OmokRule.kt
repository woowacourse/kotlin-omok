package omok.domain.rule

import omok.domain.board.OmokBoard
import omok.domain.stone.Stone

interface OmokRule {
    fun isProtected(
        stone: Stone,
        board: OmokBoard,
    ): Boolean
}
