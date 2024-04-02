package woowacourse.omok.ui

import woowacourse.omok.model.state.GameState

interface GamePlayHandler {
    fun onUpdate(gameState: GameState)

    fun onError(throwable: Throwable)
}
