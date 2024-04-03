package woowacourse.omok.domain.model.state

abstract class RunningTurn : GameState {
    override fun running(): Boolean = true

    override fun invalidPosition(): Boolean = false

    override fun finished(): Boolean = false
}
