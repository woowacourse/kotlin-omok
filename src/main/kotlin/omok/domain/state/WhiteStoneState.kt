package omok.domain.state

object WhiteStoneState : StoneState {
    override val korean = "백"
    override fun next(): StoneState = BlackStoneState
}
