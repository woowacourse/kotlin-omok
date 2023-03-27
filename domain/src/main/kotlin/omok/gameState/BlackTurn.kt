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
            referee.isForbidden(omokBoard, point) -> throw IllegalArgumentException(ERROR_FORBIDDEN.format(point.x, point.y))
            else -> WhiteTurn(omokBoard.placeStone(point, stoneState))
        }
    }

    companion object {
        private const val ERROR_FORBIDDEN = "(%s, %s)는 금지된 수 입니다"
    }
}
