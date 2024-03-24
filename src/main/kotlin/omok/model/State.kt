package omok.model

sealed class State {
    object Running : State()

    object Finished : State()
}
