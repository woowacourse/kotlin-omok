package domain.fixture

import omok.domain.board.OmokBoard
import omok.domain.place.OmokStones
import omok.domain.rule.OmokRule
import omok.domain.rule.OmokRules
import omok.domain.rule.finder.DfsRenjuFinder
import omok.domain.rule.renjuRule.SixMokRule

fun omokBoardFixture(): OmokBoard {
    val omokStones = OmokStones()
    return OmokBoard(
        omokStones,
        object : OmokRules {
            override val rules: List<OmokRule>
                get() = listOf(SixMokRule(DfsRenjuFinder))
        },
    )
}
