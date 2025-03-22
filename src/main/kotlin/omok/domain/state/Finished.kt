package omok.domain.state

import omok.domain.stone.StoneColor
import omok.domain.stone.Stones

class Finished(
    override val blackStones: Stones,
    override val whiteStones: Stones,
    val winnerColor: StoneColor?,
) : State
