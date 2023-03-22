package omok.domain.gameState

import omok.domain.OmokBoard
import omok.domain.OmokPoint
import omok.domain.omokRule.adapter.Referee
import omok.domain.state.WhiteStoneState

class WhiteTurn(override val omokBoard: OmokBoard) : GameState {
    override val stoneState = WhiteStoneState
    override val isRunning: Boolean = true

    override fun play(point: OmokPoint): GameState {
        val referee = Referee()

        return when {
            referee.isWin(omokBoard, point, stoneState) -> WhiteWin(omokBoard.placeStone(point, stoneState))
            referee.isForbidden(omokBoard, point) -> this
            else -> BlackTurn(omokBoard.placeStone(point, stoneState))
        }
    }
}
