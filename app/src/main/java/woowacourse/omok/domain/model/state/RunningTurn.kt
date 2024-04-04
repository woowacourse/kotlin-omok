package woowacourse.omok.domain.model.state

abstract class RunningTurn : GameState {
    override fun finished(): Boolean = false
}
