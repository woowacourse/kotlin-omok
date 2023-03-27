package omok.domain.state

sealed class Turn : State {

    object Black : Turn() {
        const val color = "black"
    }
    object White : Turn() {
        const val color = "white"
    }
}
