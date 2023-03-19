package omok.domain.gameState

import omok.domain.OmokBoard
import omok.domain.OmokPoint
import omok.domain.adapter.OmokAdapter
import omok.domain.omokRule.BlackWinRule
import omok.domain.omokRule.FourRule
import omok.domain.omokRule.ThreeRule
import omok.domain.state.BlackStoneState

class BlackTurn(override val omokBoard: OmokBoard) : GameState {
    override val stoneState = BlackStoneState
    override val isRunning: Boolean = true

    override fun play(point: OmokPoint): GameState {
        val adaptBoard = OmokAdapter.adaptBoard(omokBoard)
        val adaptPoint = OmokAdapter.adaptPoint(point)

        return when {
            BlackWinRule.validate(adaptBoard, adaptPoint) -> BlackWin(omokBoard.placeStone(point, stoneState))
            ThreeRule.validate(adaptBoard, adaptPoint) -> this
            FourRule.validate(adaptBoard, adaptPoint) -> this
            else -> WhiteTurn(omokBoard.placeStone(point, stoneState))
        }
    }
}
