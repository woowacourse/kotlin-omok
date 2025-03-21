package omok.domain.state

import omok.domain.stone.StoneColor
import omok.domain.stone.Stones

class Draw(
    override val blackStones: Stones,
    override val whiteStones: Stones,
) : Finished {
    override val winnerColor: StoneColor? = null
}
