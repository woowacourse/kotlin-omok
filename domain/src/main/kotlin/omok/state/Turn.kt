package omok.state

sealed class Turn : State {
    fun opposite(): State {
        return when (this) {
            Black -> White
            White -> Black
        }
    }

    object Black : Turn()
    object White : Turn()
}
