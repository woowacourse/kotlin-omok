package omok.domain.state

import omok.domain.StoneColor
import omok.domain.Stones

class BlackWin(
    blackStones: Stones,
    whiteStones: Stones,
) : Finished(blackStones, whiteStones) {
    override val winnerColor: StoneColor = StoneColor.BLACK
}
