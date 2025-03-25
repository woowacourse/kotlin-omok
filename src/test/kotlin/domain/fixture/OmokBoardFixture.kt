package domain.fixture

import omok.domain.board.OmokBoard
import omok.domain.rule.finder.DfsRenjuFinder
import omok.domain.rule.renjuRule.RenjuRule
import omok.domain.stone.OmokStones

fun omokBoardFixture(): OmokBoard {
    val omokStones = OmokStones()
    return OmokBoard(omokStones, RenjuRule(DfsRenjuFinder))
}
