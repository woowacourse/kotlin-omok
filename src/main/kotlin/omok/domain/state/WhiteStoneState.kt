package omok.domain.state

object WhiteStoneState : StoneState {
    override fun next(): StoneState = BlackStoneState
}
