package omok.domain.gameState

import omok.domain.OmokBoard
import omok.domain.OmokPoint
import omok.domain.state.WhiteStoneState

class WhiteWin(
    override val omokBoard: OmokBoard,
) : GameState {
    override val stoneState = WhiteStoneState
    override val isRunning: Boolean = false

    override fun play(point: OmokPoint): GameState {
        throw IllegalStateException(ERROR_ALREADY_FINISHED)
    }

    companion object {
        private const val ERROR_ALREADY_FINISHED = "이미 흰 돌이 이겼기 때문에 더 이상 진행할 수 없습니다."
    }
}
