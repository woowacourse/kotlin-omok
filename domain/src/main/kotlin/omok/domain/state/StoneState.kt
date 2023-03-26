package omok.domain.state

sealed interface StoneState {
    fun next(): StoneState
}
