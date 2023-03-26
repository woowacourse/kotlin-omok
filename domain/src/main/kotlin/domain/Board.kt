package domain

class Board(
    initStones: Stones = Stones(listOf()),
    private val rule: Rule,
) {
    var stones: Stones = initStones
        private set

    fun placeStone(stone: Stone) {
        stones = stones.addStone(stone)
    }

    fun canPlace(stone: Stone): Boolean {
        return !stones.isContainSamePositionStone(stone.position)
    }

    fun getWinnerColor(): Color? {
        return rule.getWinner(stones)
    }

    fun removeAllStones() {
        stones = Stones(listOf())
    }

    companion object {
        private const val BOARD_SIZE = 15
        fun getSize(): Int = BOARD_SIZE
    }
}
