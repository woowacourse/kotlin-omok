package omok.state

sealed class Turn : State {
    override fun toString(): String {
        return when (this) {
            Black -> "black"
            White -> "white"
        }
    }

    object Black : Turn()
    object White : Turn()
}
