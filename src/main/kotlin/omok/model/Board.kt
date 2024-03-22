package omok.model

class Board(private val size: Int = DEFAULT_BOARD_SIZE, val stones: Map<Position, OmokStone>) {
    init {
        validate(stones)
    }

    private val boardRage: IntRange get() = MIN..size

    private fun validate(position: Position) {
        require(position.x in boardRage && position.y in boardRage) { "Position 은 ($MIN, $MIN) ~ ($size, $size) 사이여야 한다" }
    }

    private fun validate(stones: Map<Position, OmokStone>) {
        stones.keys.forEach(::validate)
    }

    operator fun plus(stone: OmokStone): Board {
        validate(stone.position)
        return Board(size, stones + (stone.position to stone))
    }

    fun canPlace(stone: OmokStone): Boolean {
        return isInRange(stone) && isEmptyPosition(stone)
    }

    private fun isInRange(stone: OmokStone): Boolean {
        val position = stone.position
        return (position.x in boardRage) && (position.y in boardRage)
    }

    private fun isEmptyPosition(stone: OmokStone): Boolean = stones.contains(stone.position).not()

    operator fun get(position: Position): OmokStone? = stones[position]

    fun lastOrNull(): OmokStone? = stones.entries.lastOrNull()?.value

    fun isInOmok(position: Position): Boolean {
        val stone = stones[position] ?: return false
        return vectors.any { vector ->
            isInOmok(stone, vector)
        }
    }

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
        private const val DEFAULT_BOARD_SIZE = 15
        private const val MIN = 1
        private const val INITIAL_COUNT = 0
        private const val OMOK_THRESHOLD = 4
        private val OMOK_CANDIDATE_RANGE = 0..3
        private val vectors =
            listOf(
                Vector(1, 1),
                Vector(1, -1),
                Vector(0, 1),
                Vector(1, 0),
            )
    }
}
