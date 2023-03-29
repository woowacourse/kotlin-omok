package omok.state

object WhiteStoneState : StoneState {
    override fun next(): StoneState = BlackStoneState
}
