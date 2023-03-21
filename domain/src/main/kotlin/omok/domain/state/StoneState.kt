package omok.domain.state

interface StoneState {
    fun next(): StoneState
}
