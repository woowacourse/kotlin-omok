package woowacourse.omok.model

class Board(val stones: Map<Position, OmokStone>, private val maxSize: Int = 15) {
    val lastStone: OmokStone?
        get() = stones.entries.lastOrNull()?.value

    operator fun plus(stone: OmokStone): Board {
        validate(stone.position)
        return Board(stones + (stone.position to stone))
    }

    private fun validate(position: Position) {
        require(position.x in MIN..maxSize) { "x는 $MIN ~ $maxSize 사이여야 한다" }
        require(position.y in MIN..maxSize) { "y는 $MIN ~ $maxSize 사이여야 한다" }
    }

    fun isInRange(stone: OmokStone): Boolean {
        val position = stone.position
        return (position.x in MIN..maxSize) && (position.y in MIN..maxSize)
    }

    operator fun get(position: Position): OmokStone? = stones[position]

    fun isInOmok(position: Position): Boolean {
        val stone = stones[position] ?: return false
        return Vector.entries.any { vector ->
            isInOmok(stone, vector)
        }
    }

    fun isEmptyPosition(stone: OmokStone): Boolean = stones.contains(stone.position).not()

    private fun isInOmok(
        stone: OmokStone,
        vector: Vector,
    ): Boolean {
        return sumLeft(stone, vector) + sumRight(stone, vector) >= OMOK_THRESHOLD
    }

    private fun sumRight(
        stone: OmokStone,
        vector: Vector,
    ): Int {
        val (position, color) = stone
        var now = position
        var count = INITIAL_COUNT
        for (i in OMOK_CANDIDATE_RANGE) {
            now += vector
            if (stones[now]?.color == color) {
                count++
            } else {
                break
            }
        }
        return count
    }

    private fun sumLeft(
        stone: OmokStone,
        vector: Vector,
    ): Int {
        val (position, color) = stone
        var now = position
        var count = INITIAL_COUNT
        for (i in OMOK_CANDIDATE_RANGE) {
            now -= vector
            if (stones[now]?.color == color) {
                count++
            } else {
                break
            }
        }
        return count
    }

    companion object {
        private const val MIN = 1
        private const val INITIAL_COUNT = 0
        private const val OMOK_THRESHOLD = 4
        private val OMOK_CANDIDATE_RANGE = 0..3
    }
}
