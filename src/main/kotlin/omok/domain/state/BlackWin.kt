package omok.domain.state

import omok.domain.stone.BlackStones
import omok.domain.stone.StoneColor
import omok.domain.stone.WhiteStones

class BlackWin(
    blackStones: BlackStones,
    whiteStones: WhiteStones,
) : Finished(blackStones, whiteStones) {
    override val winnerColor: StoneColor = StoneColor.BLACK
}
