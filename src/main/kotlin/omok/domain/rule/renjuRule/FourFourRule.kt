package omok.domain.rule.renjuRule

import omok.domain.board.OmokBoard
import omok.domain.board.StoneStatus
import omok.domain.point.Point
import omok.domain.rule.OmokRule

object FourFourRule : OmokRule {
    private val condition = { r1: SearchResult, r2: SearchResult -> r1.stoneCount + r2.stoneCount - 1 == 4 }

    override fun isProtected(
        point: Point,
        board: OmokBoard,
    ): Boolean {
        return point.stoneStatus == StoneStatus.EMPTY && RenjuRule.searchAllDirection(
            point,
            board,
            condition,
        ) > 1
    }
}
