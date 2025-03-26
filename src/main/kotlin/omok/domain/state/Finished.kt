package omok.domain.state

import omok.domain.OmokBoard
import omok.domain.stone.StoneColor

class Finished(
    override val omokBoard: OmokBoard,
    val winnerColor: StoneColor?,
) : State
