package domain.fixture

import omok.domain.board.OmokBoard
import omok.domain.point.Point2
import omok.domain.rule.OmokRule

object FakeOmokRule : OmokRule {
    override fun isProtected(
        point: Point2,
        board: OmokBoard,
    ): Boolean {
        return true
    }
}
