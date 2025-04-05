package woowacourse.omok.domain.state

import woowacourse.omok.domain.OmokBoard
import woowacourse.omok.domain.stone.StoneColor

class WhiteTurn(
    override val omokBoard: OmokBoard,
) : Playing(omokBoard) {
    override val stoneColor: StoneColor = StoneColor.WHITE

    override fun nextTurn(omokBoard: OmokBoard): Playing = BlackTurn(omokBoard)
}
