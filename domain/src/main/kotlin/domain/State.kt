package domain

enum class State {
    WHITE, BLACK, EMPTY;

    fun nextState(): State {
        return when (this) {
            WHITE -> BLACK
            BLACK -> WHITE
            EMPTY -> EMPTY
        }
    }

    fun prevState(): State {
        return when (this) {
            WHITE -> BLACK
            BLACK -> WHITE
            EMPTY -> EMPTY
        }
    }
}
