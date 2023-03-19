package domain.domain

class Board2(
    var stones: Stones = Stones(listOf()),
) {
    fun placeStone(stone: Stone) {
        stones = stones.addStone(stone)
    }

    fun isPossibleToPlace(stone: Stone): Boolean {
        return !stones.isContainSamePositionStone(stone.position)
    }

    fun getLastPosition(): Position2? {
        if (stones.values.isEmpty()) return null
        return stones.values.last().position
    }

    companion object {
        private const val BOARD_SIZE = 15
        fun getSize(): Int = BOARD_SIZE
    }
}
