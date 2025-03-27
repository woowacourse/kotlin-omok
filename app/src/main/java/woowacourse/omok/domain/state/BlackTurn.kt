package woowacourse.omok.domain.state

import woowacourse.omok.domain.OmokBoard
import woowacourse.omok.domain.stone.StoneColor

class BlackTurn(
    override val omokBoard: OmokBoard,
) : Playing(omokBoard) {
    override val stoneColor: StoneColor = StoneColor.BLACK

    override fun nextTurn(omokBoard: OmokBoard): Playing = WhiteTurn(omokBoard)
}
