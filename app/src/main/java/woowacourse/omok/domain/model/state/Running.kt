package woowacourse.omok.domain.model.state

import woowacourse.omok.domain.controller.InvalidPositionHandler

abstract class Running : GameState {
    override fun finished(): Boolean = false

    override fun handleInvalidPosition(handler: InvalidPositionHandler): GameState {
        throw IllegalStateException("유효한 위치였습니다.")
    }
}
