package omok.domain.state

import omok.domain.StoneColor
import omok.domain.Stones

class WhiteWin(
    blackStones: Stones,
    whiteStones: Stones,
) : Finished(blackStones, whiteStones) {
    override val winnerColor: StoneColor = StoneColor.WHITE
}
