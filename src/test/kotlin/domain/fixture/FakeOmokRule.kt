package domain.fixture

import omok.domain.board.OmokBoard
import omok.domain.point.Point
import omok.domain.rule.OmokRule

object FakeOmokRule : OmokRule {
    override fun isProtected(
        point: Point,
        board: OmokBoard,
    ): Boolean {
        return true
    }
}
