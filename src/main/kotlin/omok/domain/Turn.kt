package omok.domain

class Turn(private var current: StoneType = StoneType.BLACK) {
    fun currentPlayer(): StoneType = current

    fun switch() {
        current =
            when (current) {
                StoneType.BLACK -> StoneType.WHITE
                StoneType.WHITE -> StoneType.BLACK
                else -> throw IllegalStateException("Invalid turn state")
            }
    }

    fun isBlackTurn(): Boolean = current == StoneType.BLACK
}
