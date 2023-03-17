package domain

class Players(private val value: List<Player>) {
    private var turnIndex = LAST_TURN

    fun currentTurn(): Player {
        turnPlayer()
        return value[turnIndex]
    }

    private fun turnPlayer() {
        turnIndex = if (turnIndex == value.size - LAST_TURN) INITIAL_TURN
        else turnIndex + NEXT_TURN
    }

    companion object {
        private const val INITIAL_TURN = 0
        private const val LAST_TURN = 1
        private const val NEXT_TURN = 1
    }
}
