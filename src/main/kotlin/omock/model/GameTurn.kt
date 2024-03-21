package omock.model

enum class GameTurn {
    BLACK_TURN, WHITE_TURN, FINISHED, ;

    fun turnOff(): GameTurn {
        return when (this) {
            BLACK_TURN -> WHITE_TURN
            WHITE_TURN -> BLACK_TURN
            FINISHED -> FINISHED
        }
    }

}
