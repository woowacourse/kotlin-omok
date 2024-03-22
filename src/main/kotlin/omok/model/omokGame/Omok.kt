package omok.model.omokGame

import omok.model.board.CoordsNumber
import omok.model.board.Position
import omok.model.board.Stone

class Omok(val gameBoard: Array<Array<Stone>> = Array(BOARD_SIZE) { Array(BOARD_SIZE) { Stone.EMPTY } }) {
    private var omokGameState = OmokGameState.RUNNING

    fun isRunning() = omokGameState == OmokGameState.RUNNING

    fun isGameOver(
        rowCoords: CoordsNumber,
        columnCoords: CoordsNumber,
        currentStone: Stone,
    ): Boolean {
        if (isFive(rowCoords, columnCoords, currentStone)) {
            gameFinish()
            return true
        }
        return false
    }

    fun setStone(
        x: CoordsNumber,
        y: CoordsNumber,
        stone: Stone,
    ) {
        gameBoard[y.number][x.number] = stone
    }

    fun checkBoard(stone: Stone): List<Position> {
        val coords = mutableListOf<Position>()
        for (y in gameBoard.indices) {
            for (x in gameBoard[y].indices) {
                if (gameBoard[y][x] != Stone.EMPTY) {
                    continue
                }
                if (forbiddenPoint(CoordsNumber(x), CoordsNumber(y), stone)) {
                    coords.add(Position(CoordsNumber(y), CoordsNumber(x)))
                }
            }
        }
        return coords
    }

    fun isNotEmpty(
        row: CoordsNumber,
        column: CoordsNumber,
    ): Boolean {
        return gameBoard[column.number][row.number] != Stone.EMPTY
    }

    fun isForbidden(
        row: CoordsNumber,
        column: CoordsNumber,
        forbiddenPositions: List<Position>,
    ): Boolean {
        return Position(column, row) in forbiddenPositions
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

    private fun gameFinish() {
        omokGameState = OmokGameState.STOP
    }

    private fun getStoneCount(
        x: CoordsNumber,
        y: CoordsNumber,
        stone: Stone,
        direction: Int,
    ): Int {
        var cnt = 1
        val (x1, y1) = x to y
        for (i in 0..1) {
            val (dx, dy) = getXY(direction * 2 + i)
            var (x, y) = x1.number to y1.number
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
        x: CoordsNumber,
        y: CoordsNumber,
        stone: Stone,
    ): Int {
        for (i in 0 until DIRECTION_HALF_COUNT) {
            val cnt = getStoneCount(x, y, stone, i)
            if (cnt >= MIN_COUNT_FOR_WIN) return cnt
        }
        return 0
    }

    private fun findEmptyPoint(
        x: CoordsNumber,
        y: CoordsNumber,
        stone: Stone,
        direction: Int,
    ): Pair<CoordsNumber, CoordsNumber>? {
        var (x, y) = x.number to y.number
        val (dx, dy) = getXY(direction)
        while (true) {
            x += dx
            y += dy
            if (isInvalid(x, y) || gameBoard[y][x] != stone) break
        }
        if (!isInvalid(x, y) && gameBoard[y][x] == Stone.EMPTY) {
            return CoordsNumber(x) to CoordsNumber(y)
        } else {
            return null
        }
    }

    private fun openThree(
        x: CoordsNumber,
        y: CoordsNumber,
        stone: Stone,
        direction: Int,
    ): Boolean {
        for (i in 0..1) {
            findEmptyPoint(x, y, stone, direction * 2 + i)?.let { (dx, dy) ->
                setStone(dx, dy, stone)
                if (openFour(dx, dy, stone, direction) == 1) {
                    if (!forbiddenPoint(dx, dy, stone)) {
                        setStone(dx, dy, Stone.EMPTY)
                        return true
                    }
                }
                setStone(dx, dy, Stone.EMPTY)
            }
        }
        return false
    }

    private fun openFour(
        x: CoordsNumber,
        y: CoordsNumber,
        stone: Stone,
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
        x: CoordsNumber,
        y: CoordsNumber,
        stone: Stone,
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
        x: CoordsNumber,
        y: CoordsNumber,
        stone: Stone,
        direction: Int,
    ): Boolean {
        return getStoneCount(x, y, stone, direction) == MIN_COUNT_FOR_WIN
    }

    private fun doubleThree(
        x: CoordsNumber,
        y: CoordsNumber,
        stone: Stone,
    ): Boolean {
        var cnt = 0
        setStone(x, y, stone)
        for (i in 0 until DIRECTION_HALF_COUNT) {
            if (openThree(x, y, stone, i)) cnt++
        }
        setStone(x, y, Stone.EMPTY)
        if (cnt >= 2) {
            return true
        }
        return false
    }

    private fun doubleFour(
        x: CoordsNumber,
        y: CoordsNumber,
        stone: Stone,
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

        setStone(x, y, Stone.EMPTY)
        if (cnt >= 2) {
            return true
        }
        return false
    }

    private fun forbiddenPoint(
        x: CoordsNumber,
        y: CoordsNumber,
        stone: Stone,
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
        x: CoordsNumber,
        y: CoordsNumber,
        stone: Stone,
    ): Boolean {
        for (i in 0 until DIRECTION_HALF_COUNT) {
            if (getStoneCount(x, y, stone, i) == MIN_COUNT_FOR_WIN) return true
        }
        return false
    }

    companion object {
        const val BOARD_SIZE = 15
        const val MIN_COUNT_FOR_WIN = 5
        const val DIRECTION_HALF_COUNT = 4
    }
}
