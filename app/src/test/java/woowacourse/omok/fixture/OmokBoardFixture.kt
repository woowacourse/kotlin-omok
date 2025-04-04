package woowacourse.omok.fixture

import woowacourse.omok.domain.board.OmokBoard
import woowacourse.omok.domain.point.OmokPoints

fun omokBoardFixture(): OmokBoard {
    val omokPoints = OmokPoints()
    return OmokBoard(omokPoints)
}
