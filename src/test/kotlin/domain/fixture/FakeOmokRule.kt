package domain.fixture

import omok.domain.board.OmokBoard
import omok.domain.rule.OmokRule
import omok.domain.stone.Stone

object FakeOmokRule : OmokRule {
    override fun isProtected(
        stone: Stone,
        board: OmokBoard,
    ): Boolean {
        return true
    }
}
