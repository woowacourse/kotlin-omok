package omok.domain.rule.renjuRule

import omok.domain.board.OmokBoard
import omok.domain.board.StoneStatus
import omok.domain.point.Point
import omok.domain.rule.OmokRule

object ThreeThreeRule : OmokRule {
    private val condition = { r1:SearchResult, r2:SearchResult ->
        r1.stoneCount + r2.stoneCount - 1 == 3
            && !r1.isClosed
            && !r2.isClosed
            && (!r1.isIndirectlyClosed && !r2.isIndirectlyClosed)
    }

    override fun isProtected(point: Point, board: OmokBoard): Boolean {
        return point.stoneStatus == StoneStatus.EMPTY &&
            RenjuRule.searchAllDirection(point, board, condition) > 1
    }
}