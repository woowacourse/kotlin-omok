package omok.domain.state

sealed class Turn : State {

    object Black : Turn() {
        val color = "black"
    }
    object White : Turn() {
        val color = "white"
    }
}
