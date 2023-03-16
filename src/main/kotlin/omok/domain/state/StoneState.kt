package omok.domain.state

interface StoneState {
    val korean: String
    fun next(): StoneState
}
