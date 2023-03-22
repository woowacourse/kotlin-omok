package omok.gameState

import omok.OmokBoard
import omok.OmokPoint
import omok.state.StoneState

interface GameState {
    val stoneState: StoneState
    val omokBoard: OmokBoard
    val isRunning: Boolean
    fun play(point: OmokPoint): GameState
}
