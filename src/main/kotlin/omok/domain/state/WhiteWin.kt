package omok.domain.state

import omok.domain.StoneColor
import omok.domain.stones.BlackStones
import omok.domain.stones.WhiteStones

class WhiteWin(
    blackStones: BlackStones,
    whiteStones: WhiteStones,
) : Finished(blackStones, whiteStones) {
    override val winnerColor: StoneColor = StoneColor.WHITE
}
