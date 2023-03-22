package omok.gameState

import omok.OmokBoard
import omok.OmokPoint
import omok.omokRule.adapter.Referee
import omok.state.BlackStoneState

class BlackTurn(override val omokBoard: OmokBoard) : GameState {
    override val stoneState = BlackStoneState
    override val isRunning: Boolean = true

    override fun play(point: OmokPoint): GameState {
        val referee = Referee()

        return when {
            referee.isWin(omokBoard, point, stoneState) -> BlackWin(omokBoard.placeStone(point, stoneState))
            referee.isForbidden(omokBoard, point) -> this
            else -> WhiteTurn(omokBoard.placeStone(point, stoneState))
        }
    }
}
