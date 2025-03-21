package omok.domain.state

import omok.domain.stone.BlackStones
import omok.domain.stone.StoneColor
import omok.domain.stone.WhiteStones

class WhiteWin(
    override val blackStones: BlackStones,
    override val whiteStones: WhiteStones
) : Finished {
    override val winnerColor: StoneColor = StoneColor.WHITE
}
