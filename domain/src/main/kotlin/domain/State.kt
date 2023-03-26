package domain

sealed class State {
    object Running : State()
    object Finished : State()
}
