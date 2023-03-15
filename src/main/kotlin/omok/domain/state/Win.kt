package omok.domain.state

sealed class Win : State {

    object Black : Win()
    object White : Win()
}
