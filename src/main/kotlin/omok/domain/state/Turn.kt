package omok.domain.state

sealed class Turn : State {

    object Black : Turn()
    object White : Turn()
}
