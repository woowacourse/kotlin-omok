package omok.domain.gameState

import omok.domain.OmokBoard
import omok.domain.OmokPoint
import omok.domain.omokRule.adapter.RuleAdapter
import omok.domain.state.BlackStoneState

class BlackTurn(override val omokBoard: OmokBoard) : GameState {
    override val stoneState = BlackStoneState
    override val isRunning: Boolean = true

    override fun play(point: OmokPoint): GameState {
        val adapter = RuleAdapter()

        return when {
            adapter.isWin(omokBoard, point, stoneState) -> BlackWin(omokBoard.placeStone(point, stoneState))
            adapter.isForbidden(omokBoard, point) -> this
            else -> WhiteTurn(omokBoard.placeStone(point, stoneState))
        }
    }
}
