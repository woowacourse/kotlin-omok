package omok.library

import omok.model.CoordsNumber
import omok.model.Empty
import omok.model.OmokGame
import omok.model.OmokStone

object RenjuRule {
    fun findForbiddenMovesForStone(
        gameBoard: Array<Array<OmokStone>>,
        omokStone: OmokStone?,
    ): List<Pair<CoordsNumber, CoordsNumber>> {
        val coords = mutableListOf<Pair<CoordsNumber, CoordsNumber>>()
        if (omokStone == null) return coords
        for (y in gameBoard.indices) {
            for (x in gameBoard[y].indices) {
                if (gameBoard[y][x] !is Empty) {
                    continue
                }
                if (isMoveForbiddenForStone(gameBoard, CoordsNumber(x), CoordsNumber(y), omokStone)) {
                    coords.add(CoordsNumber(y) to CoordsNumber(x))
                }
            }
        }
        return coords
    }

    fun isGameOver(
        gameBoard: Array<Array<OmokStone>>,
        rowCoords: CoordsNumber,
        columnCoords: CoordsNumber,
        currentOmokStone: OmokStone,
    ): Boolean {
        return isFive(gameBoard, rowCoords, columnCoords, currentOmokStone)
    }

    private fun countContinuousStonesBeyondWin(
        gameBoard: Array<Array<OmokStone>>,
        x: CoordsNumber,
        y: CoordsNumber,
        omokStone: OmokStone,
    ): Int {
        for (i in 0 until OmokGame.DIRECTION_HALF_COUNT) {
            val cnt = getStoneCount(gameBoard, x, y, omokStone, i)
            if (cnt >= OmokGame.MIN_COUNT_FOR_WIN) return cnt
        }
        return 0
    }

    private fun getStoneCount(
        gameBoard: Array<Array<OmokStone>>,
        x: CoordsNumber,
        y: CoordsNumber,
        omokStone: OmokStone,
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
                if (isInvalid(x, y) || !gameBoard[y][x].isSameType(omokStone)) {
                    break
                } else {
                    cnt++
                }
            }
        }
        return cnt
    }

    private fun isInvalid(
        x: Int,
        y: Int,
    ): Boolean {
        return x < 0 || x >= OmokGame.BOARD_SIZE || y < 0 || y >= OmokGame.BOARD_SIZE
    }

    private fun getXY(direction: Int): Pair<Int, Int> {
        val listDx = listOf(-1, 1, -1, 1, 0, 0, 1, -1)
        val listDy = listOf(0, 0, -1, 1, -1, 1, -1, 1)
        return Pair(listDx[direction], listDy[direction])
    }

    private fun checkForOpenThreeConfiguration(
        gameBoard: Array<Array<OmokStone>>,
        x: CoordsNumber,
        y: CoordsNumber,
        omokStone: OmokStone,
        direction: Int,
    ): Boolean {
        for (i in 0..1) {
            findEmptyPoint(gameBoard, x, y, omokStone, direction * 2 + i)?.let { (dx, dy) ->
                placeStoneForRuleCheck(gameBoard, dx, dy, omokStone)
                if (checkForOpenFourConfiguration(gameBoard, dx, dy, omokStone, direction) == 1) {
                    if (!isMoveForbiddenForStone(gameBoard, dx, dy, omokStone)) {
                        placeStoneForRuleCheck(gameBoard, dx, dy, Empty())
                        return true
                    }
                }
                placeStoneForRuleCheck(gameBoard, dx, dy, Empty())
            }
        }
        return false
    }

    private fun checkForOpenFourConfiguration(
        gameBoard: Array<Array<OmokStone>>,
        x: CoordsNumber,
        y: CoordsNumber,
        omokStone: OmokStone,
        direction: Int,
    ): Int {
        var cnt = 0
        if (isFive(gameBoard, x, y, omokStone)) {
            return 0
        }
        for (i in 0..1) {
            findEmptyPoint(gameBoard, x, y, omokStone, direction * 2 + i)?.let { (dx, dy) ->
                if (five(gameBoard, dx, dy, omokStone, direction)) cnt++
            }
        }
        if (cnt == 2) {
            if (getStoneCount(gameBoard, x, y, omokStone, direction) == OmokGame.DIRECTION_HALF_COUNT) cnt = 1
        } else {
            cnt = 0
        }
        return cnt
    }

    private fun four(
        gameBoard: Array<Array<OmokStone>>,
        x: CoordsNumber,
        y: CoordsNumber,
        omokStone: OmokStone,
        direction: Int,
    ): Boolean {
        for (i in 0..1) {
            findEmptyPoint(gameBoard, x, y, omokStone, direction * 2 + i)?.let { (dx, dy) ->
                if (five(gameBoard, dx, dy, omokStone, direction)) return true
            }
        }
        return false
    }

    private fun five(
        gameBoard: Array<Array<OmokStone>>,
        x: CoordsNumber,
        y: CoordsNumber,
        omokStone: OmokStone,
        direction: Int,
    ): Boolean {
        return getStoneCount(gameBoard, x, y, omokStone, direction) == OmokGame.MIN_COUNT_FOR_WIN
    }

    private fun checkForDoubleThreeCondition(
        gameBoard: Array<Array<OmokStone>>,
        x: CoordsNumber,
        y: CoordsNumber,
        omokStone: OmokStone,
    ): Boolean {
        var cnt = 0
        placeStoneForRuleCheck(gameBoard, x, y, omokStone)
        for (i in 0 until OmokGame.DIRECTION_HALF_COUNT) {
            if (checkForOpenThreeConfiguration(gameBoard, x, y, omokStone, i)) cnt++
        }
        placeStoneForRuleCheck(gameBoard, x, y, Empty())
        if (cnt >= 2) {
            return true
        }
        return false
    }

    private fun checkForDoubleFourCondition(
        gameBoard: Array<Array<OmokStone>>,
        x: CoordsNumber,
        y: CoordsNumber,
        omokStone: OmokStone,
    ): Boolean {
        var cnt = 0
        placeStoneForRuleCheck(gameBoard, x, y, omokStone)
        for (i in 0 until OmokGame.DIRECTION_HALF_COUNT) {
            if (checkForOpenFourConfiguration(gameBoard, x, y, omokStone, i) == 2) {
                cnt += 2
            } else if (four(gameBoard, x, y, omokStone, i)) {
                cnt += 1
            }
        }

        placeStoneForRuleCheck(gameBoard, x, y, Empty())
        if (cnt >= 2) {
            return true
        }
        return false
    }

    private fun isMoveForbiddenForStone(
        gameBoard: Array<Array<OmokStone>>,
        x: CoordsNumber,
        y: CoordsNumber,
        omokStone: OmokStone,
    ): Boolean {
        if (isFive(gameBoard, x, y, omokStone)) {
            return false
        } else if (countContinuousStonesBeyondWin(gameBoard, x, y, omokStone) > OmokGame.MIN_COUNT_FOR_WIN) {
            return true
        } else if (checkForDoubleThreeCondition(gameBoard, x, y, omokStone) ||
            checkForDoubleFourCondition(
                gameBoard,
                x,
                y,
                omokStone,
            )
        ) {
            return true
        }
        return false
    }

    private fun isFive(
        gameBoard: Array<Array<OmokStone>>,
        x: CoordsNumber,
        y: CoordsNumber,
        omokStone: OmokStone,
    ): Boolean {
        for (i in 0 until OmokGame.DIRECTION_HALF_COUNT) {
            if (getStoneCount(gameBoard, x, y, omokStone, i) == OmokGame.MIN_COUNT_FOR_WIN) return true
        }
        return false
    }

    private fun findEmptyPoint(
        gameBoard: Array<Array<OmokStone>>,
        x: CoordsNumber,
        y: CoordsNumber,
        omokStone: OmokStone,
        direction: Int,
    ): Pair<CoordsNumber, CoordsNumber>? {
        var (x, y) = x.number to y.number
        val (dx, dy) = getXY(direction)
        while (true) {
            x += dx
            y += dy
            if (isInvalid(x, y) || !gameBoard[y][x].isSameType(omokStone)) break
        }
        if (!isInvalid(x, y) && gameBoard[y][x] == Empty()) {
            return CoordsNumber(x) to CoordsNumber(y)
        } else {
            return null
        }
    }

    private fun placeStoneForRuleCheck(
        gameBoard: Array<Array<OmokStone>>,
        x: CoordsNumber,
        y: CoordsNumber,
        omokStone: OmokStone,
    ) {
        gameBoard[y.number][x.number] = omokStone
    }
}
