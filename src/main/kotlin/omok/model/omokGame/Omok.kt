package omok.model.omokGame

class Omok(val gameBoard: Array<Array<Int>> = Array(15) { Array(15) { 0 } }) {
    private var omokGameState = OmokGameState.RUNNING

    fun isRunning() = omokGameState == OmokGameState.RUNNING

    fun setStone(
        x: Int,
        y: Int,
        stone: Int,
    ) {
        gameBoard[y][x] = stone
    }

    fun checkBoard(stone: Int): List<Pair<Int, Int>> {
        val coords = mutableListOf<Pair<Int, Int>>()
        for (y in gameBoard.indices) {
            for (x in gameBoard[y].indices) {
                if (gameBoard[y][x] != EMPTY) {
                    continue
                }
                if (forbiddenPoint(x, y, stone)) {
                    coords.add(y to x)
                }
            }
        }
        return coords
    }

    fun isNotEmpty(
        row: Int,
        column: Int,
    ): Boolean {
        return gameBoard[column][row] != EMPTY
    }

    fun isForbidden(
        row: Int,
        column: Int,
        forbiddenPositions: List<Pair<Int, Int>>,
    ): Boolean {
        return column to row in forbiddenPositions
    }

    private fun isInvalid(
        x: Int,
        y: Int,
    ): Boolean {
        return x < 0 || x >= BOARD_SIZE || y < 0 || y >= BOARD_SIZE
    }

    private fun getXY(direction: Int): Pair<Int, Int> {
        val listDx = listOf(-1, 1, -1, 1, 0, 0, 1, -1)
        val listDy = listOf(0, 0, -1, 1, -1, 1, -1, 1)
        return Pair(listDx[direction], listDy[direction])
    }

    @Suppress("NAME_SHADOWING")
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
                if (isInvalid(x, y) || gameBoard[y][x] != stone) {
                    break
                } else {
                    cnt++
                }
            }
        }
        return cnt
    }

    private fun isLong(
        x: Int,
        y: Int,
        stone: Int,
    ): Int {
        for (i in 0 until 4) {
            val cnt = getStoneCount(x, y, stone, i)
            if (cnt >= 5) return cnt
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
            if (isInvalid(x, y) || gameBoard[y][x] != stone) break
        }
        if (!isInvalid(x, y) && gameBoard[y][x] == EMPTY) {
            return x to y
        } else {
            return null
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
            return cnt
        }
        for (i in 0..1) {
            findEmptyPoint(x, y, stone, direction * 2 + i)?.let { (dx, dy) ->
                if (five(dx, dy, stone, direction)) cnt++
            }
        }
        if (cnt == 2) {
            if (getStoneCount(x, y, stone, direction) == 4) cnt = 1
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
        return getStoneCount(x, y, stone, direction) == 5
    }

    private fun doubleThree(
        x: Int,
        y: Int,
        stone: Int,
    ): Boolean {
        var cnt = 0
        setStone(x, y, stone)
        for (i in 0 until 4) {
            if (openThree(x, y, stone, i)) cnt++
        }
        setStone(x, y, EMPTY)
        if (cnt >= 2) {
            return true
        }
        return false
    }

    private fun doubleFour(
        x: Int,
        y: Int,
        stone: Int,
    ): Boolean {
        var cnt = 0
        setStone(x, y, stone)
        for (i in 0 until 4) {
            if (openFour(x, y, stone, i) == 2) {
                cnt += 2
            } else if (four(x, y, stone, i)) {
                cnt += 1
            }
        }

        setStone(x, y, EMPTY)
        if (cnt >= 2) {
            return true
        }
        return false
    }

    private fun forbiddenPoint(
        x: Int,
        y: Int,
        stone: Int,
    ): Boolean {
        if (isFive(x, y, stone)) {
            omokGameState = OmokGameState.STOP
            return false
        }
        if (isLong(x, y, stone) > 5) {
            return true
        }
        if (doubleThree(x, y, stone) || doubleFour(x, y, stone)) {
            return true
        }
        return false
    }

    private fun isFive(
        x: Int,
        y: Int,
        stone: Int,
    ): Boolean {
        for (i in 0 until 4) {
            if (getStoneCount(x, y, stone, i) == 5) return true
        }
        return false
    }

    companion object {
        const val BOARD_SIZE = 15
        const val EMPTY = 0
    }
}
