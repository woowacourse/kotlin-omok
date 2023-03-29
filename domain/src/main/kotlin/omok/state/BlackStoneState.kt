package omok.state

object BlackStoneState : StoneState {
    override fun next(): StoneState = WhiteStoneState
}
