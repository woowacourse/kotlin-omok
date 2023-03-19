package omok.domain.gameState

import omok.domain.OmokBoard
import omok.domain.OmokPoint
import omok.domain.adapter.OmokAdapter
import omok.domain.omokRule.WhiteWinRule
import omok.domain.state.WhiteStoneState

class WhiteTurn(override val omokBoard: OmokBoard) : GameState {
    override val stoneState = WhiteStoneState
    override val isRunning: Boolean = true

    override fun play(point: OmokPoint): GameState {
        val adaptBoard = OmokAdapter.adaptBoard(omokBoard)
        val adaptPoint = OmokAdapter.adaptPoint(point)

        return when {
            WhiteWinRule.validate(adaptBoard, adaptPoint) -> WhiteWin(omokBoard.placeStone(point, stoneState))
            else -> BlackTurn(omokBoard.placeStone(point, stoneState))
        }
    }
}
