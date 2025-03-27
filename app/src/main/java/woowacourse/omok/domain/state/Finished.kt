package woowacourse.omok.domain.state

import woowacourse.omok.domain.OmokBoard
import woowacourse.omok.domain.stone.StoneColor

class Finished(
    override val omokBoard: OmokBoard,
    val winnerColor: StoneColor?,
) : State
