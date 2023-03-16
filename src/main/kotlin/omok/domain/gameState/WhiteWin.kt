package omok.domain.gameState

import omok.domain.OmokBoard
import omok.domain.OmokPoint
import omok.domain.state.WhiteStoneState

class WhiteWin(
    override val omokBoard: OmokBoard,
) : GameState {
    override val stoneState = WhiteStoneState
    override val isRunning: Boolean = false

    override fun play(point: OmokPoint): GameState {
        throw IllegalStateException()
    }
}
