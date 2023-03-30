package omok.state

sealed class Win : State {

    object Black : Win()
    object White : Win()
}
