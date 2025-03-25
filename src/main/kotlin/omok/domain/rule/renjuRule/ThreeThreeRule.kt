package omok.domain.rule.renjuRule

import omok.domain.board.OmokBoard
import omok.domain.place.Place
import omok.domain.rule.OmokRule
import omok.domain.rule.finder.Finder
import omok.domain.rule.finder.SearchResult

class ThreeThreeRule(val finder: Finder) : OmokRule {
    private val condition = { r1: SearchResult, r2: SearchResult ->
        r1.stoneCount + r2.stoneCount - 1 == RULE_STONE_COUNT &&
            !r1.isClosed &&
            !r2.isClosed && !(r1.isIndirectlyClosed && r2.isIndirectlyClosed)
    }

    override fun isProtected(
        place: Place,
        board: OmokBoard,
    ): Boolean {
        return finder.count(place, board, condition) > LIMIT_COUNT_OF_CONDITION
    }

    companion object {
        private const val RULE_STONE_COUNT = 3
        private const val LIMIT_COUNT_OF_CONDITION = 1
    }
}
