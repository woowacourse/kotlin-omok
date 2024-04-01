package omok.model

class Board(val size: Int, private val rule: Rule) {
    private var stones: Stones = Stones()
    private var _customBoard = Array(size) { Array(size) { 0 } }

    val customBoard: Array<Array<Int>>
        get() = _customBoard.clone()

    fun add(stone: Stone) {
        require(!duplicatedPoint(stone)) { ERROR_DUPLICATED_POINT }
        stones.add(stone)
        updateCustomBoard(stone)
    }

    fun resetCustomBoard() {
        _customBoard = Array(size) { Array(size) { 0 } }
        stones = Stones()
    }

    private fun duplicatedPoint(stone: Stone): Boolean = stones.match(stone)

    fun pointEmpty(point: Point): Boolean = !stones.occupied(point)

    fun lastStone(): Stone? = stones.lastStone()

    fun lastColor(): Color {
        return if (lastStone() != null) {
            lastStone()!!.color
        } else {
            Color.WHITE
        }
    }

    private fun updateCustomBoard(stone: Stone) {
        _customBoard[stone.point.row][stone.point.col] =
            (if (stone.color.isWhite()) 2 else 1)
    }

    fun isValidPoint(
        turn: Turn,
        point: Point,
    ): Boolean {
        return if (turn.isBlack()) {
            !rule.isInvalid(stones, Stone(point, turn.color()), customBoard)
        } else {
            true
        }
    }

    fun checkEndCondition(): Boolean {
        return if (lastStone() != null) {
            checkContinuity(lastStone()!!)
        } else {
            false
        }
    }

    fun checkContinuity(stone: Stone): Boolean {
        directions.forEach { direction ->
            var count = 1

            count += countStones(stone, direction)
            count += countStones(stone, listOf(-direction[0], -direction[1]))

            if (count >= 5) return true
        }
        return false
    }

    private fun countStones(
        stone: Stone,
        direction: List<Int>,
    ): Int {
        var currentPoint = Pair(stone.point.row + direction[0], stone.point.col + direction[1])
        var count = 0

        while (currentPoint.first in MIN_BOARD_RANGE until size && currentPoint.second in MIN_BOARD_RANGE until size &&
            stones.stones.any { it == Stone(Point(currentPoint.first, currentPoint.second), stone.color) }
        ) {
            count++
            currentPoint = Pair(currentPoint.first + direction[0], currentPoint.second + direction[1])
        }

        return count
    }

    companion object {
        private const val ERROR_DUPLICATED_POINT = "이미 수가 놓여진 곳에는 수를 놓으실 수 없습니다. 다른 위치에 수를 놓아주세요."

        private const val MIN_BOARD_RANGE = 0

        fun getSize(): Int = 15

        private val directions = listOf(listOf(1, 0), listOf(1, 1), listOf(0, 1), listOf(1, -1))
    }
}
