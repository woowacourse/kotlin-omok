package omok.domain.state

object BlackStoneState : StoneState {
    override val korean = "흑"

    override fun next(): StoneState = WhiteStoneState
}
