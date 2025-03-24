package omok.domain.rule.renjuRule

import omok.domain.board.OmokBoard
import omok.domain.point.Point
import omok.domain.rule.OmokRule
import omok.domain.rule.finder.Finder

class RenjuRule(val finder: Finder) : OmokRule {
    override fun isProtected(
        point: Point,
        board: OmokBoard,
    ): Boolean {
        return ThreeThreeRule(finder).isProtected(point, board) ||
            FourFourRule(finder).isProtected(point, board) ||
            SixMokRule(finder).isProtected(point, board)
    }
}
