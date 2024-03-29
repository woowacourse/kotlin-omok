package omok.library

import omok.model.Black
import omok.model.Empty
import woowacourse.omok.model.OmokGame
import omok.model.OmokStone
import omok.model.OmokStoneType
import omok.model.White

object RenjuRule {
    fun findForbiddenMoves(
        gameBoard: Array<Array<OmokStone>>,
        omokStone: OmokStone?,
    ): List<Pair<Int, Int>> {

        val coords = mutableListOf<Pair<Int, Int>>()
        if (omokStone == null || omokStone.getOmokStoneType() == OmokStoneType.BLACK) return coords
        for (row in gameBoard.indices) {
            for (col in gameBoard[row].indices) {
                if (gameBoard[row][col].getOmokStoneType() != OmokStoneType.EMPTY) {
                    continue
                }
                val opposite = if(omokStone.getOmokStoneType()  == OmokStoneType.BLACK) White(omokStone.boardPosition) else Black(omokStone.boardPosition)
                if (isMoveForbiddenForStone(gameBoard, row, col,opposite)) {
                    coords.add(row to col)
                }
            }
        }
        return coords
    }

    fun isGameOver(
        gameBoard: Array<Array<OmokStone>>,
        currentOmokStone: OmokStone?,
    ): Boolean {
        if (currentOmokStone == null) return false
        return isFive(gameBoard, currentOmokStone)
    }

    private fun countContinuousStonesBeyondWin(
        gameBoard: Array<Array<OmokStone>>,
        row: Int,
        col: Int,
        omokStone: OmokStone,
    ): Int {
        placeStoneForRuleCheck(gameBoard, row, col, omokStone)
        for (i in 0 until OmokGame.DIRECTION_HALF_COUNT) {
            val cnt = getStoneCount(gameBoard, omokStone.getRow(), omokStone.getColumn(), omokStone, i)
            if (cnt >= OmokGame.MIN_COUNT_FOR_WIN) {
                placeStoneForRuleCheck(gameBoard, row, col, Empty())
                return cnt
            }
        }
        placeStoneForRuleCheck(gameBoard, row, col, Empty())
        return 0
    }



    private fun getStoneCount(
        gameBoard: Array<Array<OmokStone>>,
        row: Int,
        col: Int,
        omokStone: OmokStone,
        direction: Int,
    ): Int {
        var cnt = 1
        val (row1, col1) = row to col
        for (i in 0..1) {
            val (dx, dy) = getXY(direction * 2 + i)
            var (x, y) = row1 to col1
            while (true) {
                x += dx
                y += dy
                if (isInvalid(x, y) || !gameBoard[x][y].isSameType(omokStone)) {
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
        row: Int,
        col: Int,
        omokStone: OmokStone,
        direction: Int,
    ): Boolean {
        for (i in 0..1) {
            findEmptyPoint(gameBoard, row, col, omokStone, direction * 2 + i)?.let { (dx, dy) ->
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
        row: Int,
        col: Int,
        omokStone: OmokStone,
        direction: Int,
    ): Int {
        var cnt = 0
        if (isFive(gameBoard, omokStone)) {
            return 0
        }
        for (i in 0..1) {
            findEmptyPoint(gameBoard, row, col, omokStone, direction * 2 + i)?.let { (dx, dy) ->
                if (five(gameBoard, dx, dy, omokStone, direction)) cnt++
            }
        }
        if (cnt == 2) {
            if (getStoneCount(gameBoard, row, col, omokStone, direction) == OmokGame.DIRECTION_HALF_COUNT) cnt = 1
        } else {
            cnt = 0
        }
        return cnt
    }

    private fun four(
        gameBoard: Array<Array<OmokStone>>,
        row: Int,
        col: Int,
        omokStone: OmokStone,
        direction: Int,
    ): Boolean {
        for (i in 0..1) {
            findEmptyPoint(gameBoard, row, col, omokStone, direction * 2 + i)?.let { (dx, dy) ->
                if (five(gameBoard, dx, dy, omokStone, direction)) return true
            }
        }
        return false
    }

    private fun five(
        gameBoard: Array<Array<OmokStone>>,
        row: Int,
        col: Int,
        omokStone: OmokStone,
        direction: Int,
    ): Boolean {
        return getStoneCount(gameBoard, row, col, omokStone, direction) == OmokGame.MIN_COUNT_FOR_WIN
    }

    private fun checkForDoubleThreeCondition(
        gameBoard: Array<Array<OmokStone>>,
        row: Int,
        col: Int,
        omokStone: OmokStone,
    ): Boolean {
        var cnt = 0
        placeStoneForRuleCheck(gameBoard, row, col, omokStone)
        for (i in 0 until OmokGame.DIRECTION_HALF_COUNT) {
            if (checkForOpenThreeConfiguration(gameBoard, row, col, omokStone, i)) cnt++
        }
        placeStoneForRuleCheck(gameBoard, row, col, Empty())
        if (cnt >= 2) {
            return true
        }
        return false
    }

    private fun checkForDoubleFourCondition(
        gameBoard: Array<Array<OmokStone>>,
        row: Int,
        col: Int,
        omokStone: OmokStone,
    ): Boolean {
        var cnt = 0
        placeStoneForRuleCheck(gameBoard, row, col, omokStone)
        for (i in 0 until OmokGame.DIRECTION_HALF_COUNT) {
            if (checkForOpenFourConfiguration(gameBoard, row, col, omokStone, i) == 2) {
                cnt += 2
            } else if (four(gameBoard, row, col, omokStone, i)) {
                cnt += 1
            }
        }

        placeStoneForRuleCheck(gameBoard, row, col, Empty())
        if (cnt >= 2) {
            return true
        }
        return false
    }

    private fun isMoveForbiddenForStone(
        gameBoard: Array<Array<OmokStone>>,
        row: Int,
        col: Int,
        omokStone: OmokStone,
    ): Boolean {
        if (isFiveAll(gameBoard, row, col, omokStone)) {
            return false
        } else if (countContinuousStonesBeyondWin(gameBoard, row, col, omokStone) > OmokGame.MIN_COUNT_FOR_WIN) {
            return true
        } else if (checkForDoubleThreeCondition(gameBoard, row, col, omokStone) ||
            checkForDoubleFourCondition(
                gameBoard,
                row,
                col,
                omokStone,
            )
        ) {
            return true
        }
        return false
    }

    private fun isFiveAll(
        gameBoard: Array<Array<OmokStone>>,
        row: Int,
        col: Int,
        omokStone: OmokStone,
    ): Boolean {
        placeStoneForRuleCheck(gameBoard, row, col, omokStone)
        for (i in 0 until OmokGame.DIRECTION_HALF_COUNT) {
            if (getStoneCount(
                    gameBoard,
                    omokStone.getRow(),
                    omokStone.getColumn(),
                    omokStone,
                    i,
                ) == OmokGame.MIN_COUNT_FOR_WIN
            ) {
                placeStoneForRuleCheck(gameBoard, row, col, Empty())
                return true
            }
        }
        placeStoneForRuleCheck(gameBoard, row, col, Empty())
        return false
    }



    private fun isFive(
        gameBoard: Array<Array<OmokStone>>,
        omokStone: OmokStone,
    ): Boolean {
        for (i in 0 until OmokGame.DIRECTION_HALF_COUNT) {
            if (getStoneCount(
                    gameBoard,
                    omokStone.getRow(),
                    omokStone.getColumn(),
                    omokStone,
                    i,
                ) == OmokGame.MIN_COUNT_FOR_WIN
            ) {
                return true
            }
        }
        return false
    }

    private fun findEmptyPoint(
        gameBoard: Array<Array<OmokStone>>,
        row: Int,
        col: Int,
        omokStone: OmokStone,
        direction: Int,
    ): Pair<Int, Int>? {
        var (x, y) = row to col
        val (dx, dy) = getXY(direction)
        while (true) {
            x += dx
            y += dy
            if (isInvalid(x, y) || !gameBoard[x][y].isSameType(omokStone)) break
        }
        return if (!isInvalid(x, y) && gameBoard[x][y].getOmokStoneType() == OmokStoneType.EMPTY) {
            x to y
        } else {
            null
        }
    }

    private fun placeStoneForRuleCheck(
        gameBoard: Array<Array<OmokStone>>,
        row: Int,
        col: Int,
        omokStone: OmokStone,
    ) {
        gameBoard[row][col] = omokStone
    }
}
