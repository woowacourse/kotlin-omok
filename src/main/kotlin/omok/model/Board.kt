package omok.model

class Board(private val stones: Map<Position, OmokStone>) {

    operator fun plus(
        stone: OmokStone
    ): Board {
        return Board(stones + (stone.position to stone))
    }

    operator fun get(position: Position): OmokStone? = stones[position]

    fun last(): OmokStone = stones.entries.last().value

    fun isInOmok(position: Position): Boolean {
        val stone = stones[position] ?: return false
        return vectors.any { vector ->
            isInOmok(stone, vector)
        }
    }

    private fun isInOmok(stone: OmokStone, vector: Vector): Boolean {
        return sumLeft(stone, vector) + sumRight(stone, vector) >= OMOK_THRESHOLD
    }

    private fun sumRight(stone: OmokStone, vector: Vector): Int {
        val (position, color) = stone
        var now = position
        var count = INITIAL_COUNT
        for (i in OMOK_CANDIDATE_RANGE) {
            now += vector
            if (stones[now]?.color == color) count++
            else break
        }
        return count
    }

    private fun sumLeft(stone: OmokStone, vector: Vector): Int {
        val (position, color) = stone
        var now = position
        var count = INITIAL_COUNT
        for (i in OMOK_CANDIDATE_RANGE) {
            now -= vector
            if (stones[now]?.color == color) count++
            else break
        }
        return count
    }

    companion object {
        private const val INITIAL_COUNT = 0
        private const val OMOK_THRESHOLD = 4
        private val OMOK_CANDIDATE_RANGE = 0..3
        private val vectors = listOf(
            Vector(1, 1),
            Vector(1, -1),
            Vector(0, 1),
            Vector(1, 0)
        )
    }
}
