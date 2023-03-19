package omok.domain.gameState

import omok.domain.OmokBoard
import omok.domain.OmokPoint
import omok.domain.state.StoneState

interface GameState {
    val stoneState: StoneState
    val omokBoard: OmokBoard
    val isRunning: Boolean

    fun play(point: OmokPoint): GameState
}
