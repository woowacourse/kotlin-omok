package omok.domain.gameState

import omok.domain.OmokBoard
import omok.domain.OmokPoint
import omok.domain.omokRule.BlackWinRule
import omok.domain.omokRule.FourFourRule
import omok.domain.omokRule.ThreeThreeRule
import omok.domain.state.BlackStoneState
import omok.domain.util.OmokAdapter

class BlackTurn(override val omokBoard: OmokBoard) : GameState {
    override val stoneState = BlackStoneState
    override val isRunning: Boolean = true

    override fun play(point: OmokPoint): GameState {
        val adaptedBoard = OmokAdapter.adaptOmokBoard(omokBoard)
        val adaptedPoint = OmokAdapter.adaptOmokPoint(point)
        return when {
            BlackWinRule.validate(adaptedBoard, adaptedPoint) -> BlackWin(omokBoard.placeStone(point, stoneState))
            FourFourRule.validate(adaptedBoard, adaptedPoint) -> throw IllegalStateException(ERROR_FOUR_FOUR)
            ThreeThreeRule.validate(adaptedBoard, adaptedPoint) -> throw IllegalStateException(ERROR_THREE_THREE)
            else -> WhiteTurn(omokBoard.placeStone(point, stoneState))
        }
    }

    companion object {
        private const val ERROR_FOUR_FOUR = "흑은 44를 놓을 수 없습니다."
        private const val ERROR_THREE_THREE = "흑은 33을 놓을 수 없습니다."
    }
}
