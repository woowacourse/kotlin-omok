package domain.domain

class Board(
    initStones: Stones = Stones(listOf()),
) {
    var stones: Stones = initStones
        private set
    private val rule: Rule
        get() = RuleAdapter(stones, getCurrentTurn())

    fun placeStone(stone: Stone) {
        stones = stones.addStone(stone)
    }

    fun isEmpty(stone: Stone): Boolean {
        return !stones.isContainSamePositionStone(stone.position)
    }

    fun getLastPosition(): Position? {
        if (stones.values.isEmpty()) return null
        return stones.values.last().position
    }

    fun isBlackWin(stone: Stone): Boolean {
        return rule.checkBlackWin(stone)
    }

    fun isWhiteWin(stone: Stone): Boolean {
        if (rule.checkInvalid(stone)) return true
        return rule.checkWhiteWin(stone)
    }

    fun getCurrentTurn(): Color {
        if (stones.getBlackStonesCount() > stones.getWhiteStonesCount()) return Color.WHITE
        return Color.BLACK
    }

    companion object {
        private const val BOARD_SIZE = 15
        fun getSize(): Int = BOARD_SIZE
    }
}
