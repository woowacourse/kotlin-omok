package domain.fixture

import omok.domain.board.OmokBoard
import omok.domain.point.OmokPoints
import omok.domain.rule.finder.DfsRenjuFinder
import omok.domain.rule.renjuRule.RenjuRule

fun omokBoardFixture(): OmokBoard {
    val omokPoints = OmokPoints()
    return OmokBoard(omokPoints, RenjuRule(DfsRenjuFinder))
}
