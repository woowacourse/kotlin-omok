package woowacourse.omok.domain.model.state

import woowacourse.omok.domain.model.StonePosition

abstract class Running : GameState {
    override fun finished(): Boolean = false

    override fun handleInvalidPosition(handling: (StonePosition, InvalidPositionState) -> Unit): GameState {
        throw IllegalStateException("유효한 위치였습니다.")
    }
}
