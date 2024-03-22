package omok.model.board

import omok.model.OmokStone
import omok.model.Position
import omok.model.Vector

class Board(val size: BoardSize = DEFAULT_BOARD_SIZE, val boxes: Boxes = Boxes()) {
    val positions get() = boxes.keys
    val stones get() = boxes.values

    init {
        validate(boxes)
    }

    private fun validate(boxes: Map<Position, OmokStone>) {
        boxes.keys.forEach(::validate)
    }

    private fun validate(position: Position) {
        require(
            size.isInBounds(
                position.x,
                position.y,
            ),
        ) { "Position 은 ($MIN, $MIN) ~ (${size.width}, ${size.width}) 사이여야 한다" }
    }

    operator fun plus(stone: OmokStone): Board {
        validate(stone.position)
        return Board(size, boxes + stone)
    }

    fun canPlace(stone: OmokStone): Boolean {
        return isInBounds(stone) && isEmptyPosition(stone)
    }

    private fun isInBounds(stone: OmokStone): Boolean {
        val position = stone.position
        return size.isInBounds(
            position.x,
            position.y,
        )
    }

    private fun isEmptyPosition(stone: OmokStone): Boolean = (stone.position !in boxes)

    operator fun get(position: Position): OmokStone? = boxes[position]

    fun lastOrNull(): OmokStone? = boxes.entries.lastOrNull()?.value

    fun isInOmok(position: Position): Boolean {
        val stone = boxes[position] ?: return false
        return Vector.FOUR_DIRECTIONS.any { vector ->
            isInOmok(stone, vector)
        }
    }

    private fun isInOmok(
        stone: OmokStone,
        vector: Vector,
    ): Boolean {
        return sumSameStone(stone, vector) + sumSameStone(stone, -vector) >= OMOK_THRESHOLD
    }

    private fun sumSameStone(
        stone: OmokStone,
        vector: Vector,
    ): Int {
        val (position, color) = stone
        var now = position
        var count = INITIAL_COUNT
        for (i in OMOK_CANDIDATE_RANGE) {
            now += vector
            if (boxes[now]?.color != color) break
            count++
        }
        return count
    }

    companion object {
        private val DEFAULT_BOARD_SIZE = BoardSize(15)
        private const val MIN = 1
        private const val INITIAL_COUNT = 0
        private const val OMOK_THRESHOLD = 4
        private val OMOK_CANDIDATE_RANGE = 0..3
    }
}
