package omok.domain.state

object BlackStoneState : StoneState {
    override fun next(): StoneState = WhiteStoneState
}
