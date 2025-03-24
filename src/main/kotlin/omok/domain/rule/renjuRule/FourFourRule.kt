package omok.domain.rule.renjuRule

import omok.domain.board.OmokBoard
import omok.domain.point.Point2
import omok.domain.rule.OmokRule
import omok.domain.rule.finder.Finder
import omok.domain.rule.finder.SearchResult

class FourFourRule(val finder: Finder) : OmokRule {
    private val condition = { r1: SearchResult, r2: SearchResult -> r1.stoneCount + r2.stoneCount - 1 == RULE_STONE_COUNT }

    override fun isProtected(
        point: Point2,
        board: OmokBoard,
    ): Boolean {
        return finder.count(point, board, condition) > LIMIT_COUNT_OF_CONDITION
    }

    companion object {
        private const val RULE_STONE_COUNT = 4
        private const val LIMIT_COUNT_OF_CONDITION = 1
    }
}
