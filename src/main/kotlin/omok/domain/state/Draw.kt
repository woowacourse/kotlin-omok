package omok.domain.state

import omok.domain.stone.BlackStones
import omok.domain.stone.StoneColor
import omok.domain.stone.WhiteStones

class Draw(
    override val blackStones: BlackStones,
    override val whiteStones: WhiteStones,
) : Finished {
    override val winnerColor: StoneColor? = null
}
