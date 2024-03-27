package omok.lib

class RenjuGameRule : GameRule {
    private lateinit var board: Array<Array<Int>>

    override fun setupBoard(board: Array<Array<Int>>) {
        this.board = board
    }

    override fun setStone(
        x: Int,
        y: Int,
        stone: Int,
    ) {
        board[y][x] = stone
    }

    override fun isWinningMove(
        rowCoords: Int,
        columnCoords: Int,
        stone: Int,
    ): Boolean {
        if (stone == WHITE) return isLong(rowCoords, columnCoords, stone) >= 5
        return isFive(rowCoords, columnCoords, stone)
    }

    override fun findForbiddenPositions(stone: Int): List<Pair<Int, Int>> {
        val coords = mutableListOf<Pair<Int, Int>>()
        for (y in board.indices) {
            for (x in board[y].indices) {
                if (board[y][x] != EMPTY) {
                    continue
                }
                if (forbiddenPoint(x, y, stone)) {
                    coords.add(y to x)
                }
            }
        }
        return coords
    }

    override fun isMoveAllowed(
        board: Array<Array<Int>>,
        rowCoords: Int,
        columnCoords: Int,
        stone: Int,
        forbiddenPositions: List<Pair<Int, Int>>,
    ): Boolean {
        if (forbiddenPositions.contains<Pair<Any, Any>>(columnCoords to rowCoords)) {
            return false
        }
        if (rowCoords < 0 || rowCoords >= board.size ||
            columnCoords < 0 || columnCoords >= board.size
        ) {
            return false
        }
        if (board[rowCoords][columnCoords] != EMPTY) {
            return false
        }
        return true
    }

    fun returnBoard(): Array<Array<Int>> {
        return board
    }

    private fun getStoneCount(
        x: Int,
        y: Int,
        stone: Int,
        direction: Int,
    ): Int {
        var cnt = 1
        val (x1, y1) = x to y
        for (i in 0..1) {
            val (dx, dy) = getXY(direction * 2 + i)
            var (x, y) = x1 to y1
            while (true) {
                x += dx
                y += dy
                if (isOutOfBounds(x, y) || board[y][x] != stone) {
                    break
                } else {
                    cnt++
                }
            }
        }
        return cnt
    }

    private fun getXY(direction: Int): Pair<Int, Int> {
        val listDx = listOf(-1, 1, -1, 1, 0, 0, 1, -1)
        val listDy = listOf(0, 0, -1, 1, -1, 1, -1, 1)
        return Pair(listDx[direction], listDy[direction])
    }

    private fun isOutOfBounds(
        x: Int,
        y: Int,
    ): Boolean {
        return x < 0 || x >= board.size || y < 0 || y >= board.size
    }

    private fun isLong(
        x: Int,
        y: Int,
        stone: Int,
    ): Int {
        for (i in 0 until DIRECTION_HALF_COUNT) {
            val cnt = getStoneCount(x, y, stone, i)
            if (cnt >= MIN_COUNT_FOR_WIN) return cnt
        }
        return 0
    }

    private fun findEmptyPoint(
        x: Int,
        y: Int,
        stone: Int,
        direction: Int,
    ): Pair<Int, Int>? {
        var (x, y) = x to y
        val (dx, dy) = getXY(direction)
        while (true) {
            x += dx
            y += dy
            if (isOutOfBounds(x, y) || board[y][x] != stone) break
        }
        return if (!isOutOfBounds(x, y) && board[y][x] == EMPTY) {
            x to y
        } else {
            null
        }
    }

    private fun openThree(
        x: Int,
        y: Int,
        stone: Int,
        direction: Int,
    ): Boolean {
        for (i in 0..1) {
            findEmptyPoint(x, y, stone, direction * 2 + i)?.let { (dx, dy) ->
                setStone(dx, dy, stone)
                if (openFour(dx, dy, stone, direction) == 1) {
                    if (!forbiddenPoint(dx, dy, stone)) {
                        setStone(dx, dy, EMPTY)
                        return true
                    }
                }
                setStone(dx, dy, EMPTY)
            }
        }
        return false
    }

    private fun openFour(
        x: Int,
        y: Int,
        stone: Int,
        direction: Int,
    ): Int {
        var cnt = 0
        if (isFive(x, y, stone)) {
            return 0
        }
        for (i in 0..1) {
            findEmptyPoint(x, y, stone, direction * 2 + i)?.let { (dx, dy) ->
                if (five(dx, dy, stone, direction)) cnt++
            }
        }
        if (cnt == 2) {
            if (getStoneCount(x, y, stone, direction) == DIRECTION_HALF_COUNT) cnt = 1
        } else {
            cnt = 0
        }
        return cnt
    }

    private fun four(
        x: Int,
        y: Int,
        stone: Int,
        direction: Int,
    ): Boolean {
        for (i in 0..1) {
            findEmptyPoint(x, y, stone, direction * 2 + i)?.let { (dx, dy) ->
                if (five(dx, dy, stone, direction)) return true
            }
        }
        return false
    }

    private fun five(
        x: Int,
        y: Int,
        stone: Int,
        direction: Int,
    ): Boolean {
        return getStoneCount(x, y, stone, direction) == MIN_COUNT_FOR_WIN
    }

    private fun doubleThree(
        x: Int,
        y: Int,
        stone: Int,
    ): Boolean {
        var cnt = 0
        setStone(x, y, stone)
        for (i in 0 until DIRECTION_HALF_COUNT) {
            if (openThree(x, y, stone, i)) cnt++
        }
        setStone(x, y, EMPTY)
        return cnt >= 2
    }

    private fun doubleFour(
        x: Int,
        y: Int,
        stone: Int,
    ): Boolean {
        var cnt = 0
        setStone(x, y, stone)
        for (i in 0 until DIRECTION_HALF_COUNT) {
            if (openFour(x, y, stone, i) == 2) {
                cnt += 2
            } else if (four(x, y, stone, i)) {
                cnt += 1
            }
        }
        setStone(x, y, EMPTY)
        return cnt >= 2
    }

    private fun forbiddenPoint(
        x: Int,
        y: Int,
        stone: Int,
    ): Boolean {
        if (isFive(x, y, stone)) {
            return false
        } else if (isLong(x, y, stone) > MIN_COUNT_FOR_WIN) {
            return true
        } else if (doubleThree(x, y, stone) || doubleFour(x, y, stone)) {
            return true
        }
        return false
    }

    private fun isFive(
        x: Int,
        y: Int,
        stone: Int,
    ): Boolean {
        for (i in 0 until DIRECTION_HALF_COUNT) {
            if (getStoneCount(x, y, stone, i) == MIN_COUNT_FOR_WIN) return true
        }
        return false
    }

    companion object {
        const val MIN_COUNT_FOR_WIN = 5
        const val DIRECTION_HALF_COUNT = 4
        const val WHITE = -1
        const val EMPTY = 0
    }
}
