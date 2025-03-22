package omok.domain.state

import omok.domain.stone.BlackStones
import omok.domain.stone.StoneColor
import omok.domain.stone.WhiteStones

sealed class Finished(
    override val blackStones: BlackStones,
    override val whiteStones: WhiteStones,
    val winnerColor: StoneColor?,
) : State {
    class WhiteWin(blackStones: BlackStones, whiteStones: WhiteStones) :
        Finished(blackStones, whiteStones, StoneColor.WHITE)

    class BlackWin(blackStones: BlackStones, whiteStones: WhiteStones) :
        Finished(blackStones, whiteStones, StoneColor.BLACK)

    class Draw(blackStones: BlackStones, whiteStones: WhiteStones) :
        Finished(blackStones, whiteStones, null)
}
