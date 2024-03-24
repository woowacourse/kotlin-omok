package omok.lib

import omok.model.board.CoordsNumber
import omok.model.board.Position
import omok.model.board.Stone
import omok.model.omokGame.Board

class RenjuGameRule : GameRule {
    private lateinit var board: Board

    override fun setupBoard(board: Board) {
        this.board = board
    }

    override fun isWinningMove(
        rowCoords: Int,
        columnCoords: Int,
        stone: Stone,
    ): Boolean {
        if (stone == Stone.WHITE) return isLong(CoordsNumber(rowCoords), CoordsNumber(columnCoords), stone) >= 5
        return isFive(CoordsNumber(rowCoords), CoordsNumber(columnCoords), stone)
    }

    override fun findForbiddenPositions(stone: Stone): List<Pair<Int, Int>> {
        val coords = mutableListOf<Pair<Int, Int>>()
        for (y in board.gameBoard.indices) {
            for (x in board.gameBoard[y].indices) {
                if (board.gameBoard[y][x] != Stone.EMPTY) {
                    continue
                }
                if (forbiddenPoint(CoordsNumber(x), CoordsNumber(y), stone)) {
                    coords.add(y to x)
                }
            }
        }
        return coords
    }

    override fun isMoveAllowed(
        board: Array<Array<Stone>>,
        rowCoords: Int,
        columnCoords: Int,
        stone: Stone,
        forbiddenPositions: List<Pair<Int, Int>>,
    ): Boolean {
        if (forbiddenPositions.contains<Pair<Any, Any>>(CoordsNumber(columnCoords) to CoordsNumber(rowCoords))) {
            return false
        }
        if (rowCoords < 0 || rowCoords >= Board.BOARD_SIZE ||
            columnCoords < 0 || columnCoords >= Board.BOARD_SIZE
        ) {
            return false
        }
        if (board[rowCoords][columnCoords] != Stone.EMPTY) {
            return false
        }
        return true
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
                if (isOutOfBounds(x, y) || board.gameBoard[y][x] != stone) {
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
        return x < 0 || x >= Board.BOARD_SIZE || y < 0 || y >= Board.BOARD_SIZE
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
    ): Position? {
        var (x, y) = x.number to y.number
        val (dx, dy) = getXY(direction)
        while (true) {
            x += dx
            y += dy
            if (isOutOfBounds(x, y) || board.gameBoard[y][x] != stone) break
        }
        return if (!isOutOfBounds(x, y) && board.gameBoard[y][x] == Stone.EMPTY) {
            Position(CoordsNumber(x), CoordsNumber(y))
        } else {
            null
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
                board.setStone(dx, dy, stone)
                if (openFour(dx, dy, stone, direction) == 1) {
                    if (!forbiddenPoint(dx, dy, stone)) {
                        board.setStone(dx, dy, Stone.EMPTY)
                        return true
                    }
                }
                board.setStone(dx, dy, Stone.EMPTY)
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
        board.setStone(x, y, stone)
        for (i in 0 until DIRECTION_HALF_COUNT) {
            if (openThree(x, y, stone, i)) cnt++
        }
        board.setStone(x, y, Stone.EMPTY)
        return cnt >= 2
    }

    private fun doubleFour(
        x: CoordsNumber,
        y: CoordsNumber,
        stone: Stone,
    ): Boolean {
        var cnt = 0
        board.setStone(x, y, stone)
        for (i in 0 until DIRECTION_HALF_COUNT) {
            if (openFour(x, y, stone, i) == 2) {
                cnt += 2
            } else if (four(x, y, stone, i)) {
                cnt += 1
            }
        }
        board.setStone(x, y, Stone.EMPTY)
        return cnt >= 2
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
        const val MIN_COUNT_FOR_WIN = 5
        const val DIRECTION_HALF_COUNT = 4
    }
}
