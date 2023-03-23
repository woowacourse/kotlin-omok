package omok.gameState

import omok.OmokBoard
import omok.OmokPoint
import omok.omokRule.adapter.Referee
import omok.state.WhiteStoneState

class WhiteTurn(override val omokBoard: OmokBoard) : GameState {
    override val stoneState = WhiteStoneState
    override val isRunning: Boolean = true

    override fun play(point: OmokPoint): GameState {
        val referee = Referee()

        return when {
            referee.isWin(omokBoard, point, stoneState) -> WhiteWin(omokBoard.placeStone(point, stoneState))
            else -> BlackTurn(omokBoard.placeStone(point, stoneState))
        }
    }
}
