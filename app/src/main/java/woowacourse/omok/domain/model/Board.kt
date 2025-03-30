package woowacourse.omok.domain.model

import woowacourse.omok.domain.model.state.BlackTurn
import woowacourse.omok.domain.model.state.Finished
import woowacourse.omok.domain.model.state.Playing
import woowacourse.omok.domain.model.state.Ready
import woowacourse.omok.domain.model.state.State
import woowacourse.omok.domain.model.state.WhiteTurn
import woowacourse.omok.domain.model.stone.BlackStones
import woowacourse.omok.domain.model.stone.StoneColor
import woowacourse.omok.domain.model.stone.WhiteStones

class Board(
    state: State = Ready(),
    val size: Int = DEFAULT_BOARD_SIZE,
) {
    init {
        require(size >= MINIMUM_BOARD_SIZE) { ERROR_INVALID_BOARD_SIZE }
    }

    var state = state
        private set

    fun playOmok(
        onTurn: (StoneColor, Point?) -> Unit,
        onPointInput: () -> Point,
        onBoardUpdated: (BlackStones, WhiteStones) -> Unit,
    ) {
        while (true) {
            val currentState = state

            if (currentState is Playing) {
                when (currentState) {
                    is WhiteTurn -> onTurn(currentState.nextStoneColor(), currentState.blackStones.lastStonePoint)
                    is BlackTurn -> onTurn(currentState.nextStoneColor(), currentState.whiteStones.lastStonePoint)
                    else -> onTurn(currentState.nextStoneColor(), null)
                }
                state = currentState.place(onPointInput(), size, onBoardUpdated)
            }

            if (state is Finished) break
        }
    }

    companion object {
        const val DEFAULT_BOARD_SIZE = 15
        private const val MINIMUM_BOARD_SIZE = 5
        private const val ERROR_INVALID_BOARD_SIZE = "[ERROR] 오목판의 사이즈는 최소 5x5이어야 합니다."
    }
}
