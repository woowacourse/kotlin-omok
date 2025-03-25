package domain.fixture

import omok.domain.board.OmokBoard
import omok.domain.point.OmokPoints

fun omokBoardFixture(): OmokBoard {
    val omokPoints = OmokPoints()
    return OmokBoard(omokPoints)
}
