package woowacourse.omok.view

import woowacourse.omok.domain.OmokResult
import woowacourse.omok.domain.StoneColor
import woowacourse.omok.domain.grid.OmokGrid.Companion.DEFAULT_SIZE
import woowacourse.omok.domain.grid.OmokPoint

class OutputView {
    fun printErrorMessage(message: String?) {
        if (message == null) {
            println(ERROR_NOT_FOUND)
        } else {
            println(ERROR_MESSAGE.format(message))
        }
    }

    fun printStartMessage() = println(MESSAGE_GAME_START)

    fun printBoardState(
        blackStones: Set<OmokPoint>,
        whiteStones: Set<OmokPoint>,
    ) {
        val board = makeBoard(blackStones, whiteStones)
        println()
        for (row in DEFAULT_SIZE downTo MIN_BOUND) {
            printRow(board, row)
        }
        print(BLANK)
        printCoordinateY()
    }

    private fun makeBoard(
        blackStones: Set<OmokPoint>,
        whiteStones: Set<OmokPoint>,
    ): List<List<StoneColor?>> {
        val board: List<MutableList<StoneColor?>> = List(DEFAULT_SIZE + 1) { MutableList(DEFAULT_SIZE + 1) { null } }

        blackStones.forEach { (point, _) ->
            board[point.row.value][point.col.value] = StoneColor.BLACK
        }

        whiteStones.forEach { (point, _) ->
            board[point.row.value][point.col.value] = StoneColor.WHITE
        }
        return board
    }

    private fun printRow(
        grid: List<List<StoneColor?>>,
        row: Int,
    ) {
        print(COORDINATE_X.format(row))
        for (col in MIN_BOUND..DEFAULT_SIZE) {
            print(boardUI(grid, row, col))
            if (col != DEFAULT_SIZE) repeat(REPEAT_COUNT) { print(DASH) }
        }
        println()
    }

    private fun boardUI(
        grid: List<List<StoneColor?>>,
        row: Int,
        col: Int,
    ): String {
        val state = grid[row][col]
        return when {
            state != null -> state.toUI()
            row == MIN_BOUND && col == MIN_BOUND -> LEFT_DOWN
            row == MIN_BOUND && col == MAX_BOUND -> RIGHT_DOWN
            row == MAX_BOUND && col == MIN_BOUND -> LEFT_UP
            row == MAX_BOUND && col == MAX_BOUND -> RIGHT_UP
            col == MIN_BOUND -> LEFT
            row == MAX_BOUND -> UP
            col == MAX_BOUND -> RIGHT
            row == MIN_BOUND -> DOWN
            else -> MIDDLE
        }
    }

    private fun StoneColor.toUI(): String {
        return when (this) {
            StoneColor.BLACK -> "●"
            StoneColor.WHITE -> "○"
        }
    }

    private fun printCoordinateY() {
        println(('A' until 'A' + DEFAULT_SIZE).joinToString("  "))
    }

    fun printWinner(omokResult: OmokResult) {
        println(MESSAGE_WINNER.format(omokResult.toString()))
    }

    companion object {
        private const val MESSAGE_GAME_START = "오목 게임을 시작합니다."
        private const val ERROR_NOT_FOUND = "[ERROR] 유효하지 않은 접근입니다"
        private const val ERROR_MESSAGE = "!!ERROR %s!!"
        private const val REPEAT_COUNT = 2

        private const val LEFT_DOWN = "└"
        private const val LEFT = "├"
        private const val LEFT_UP = "┌"
        private const val UP = "┬"
        private const val RIGHT_UP = "┐"
        private const val RIGHT = "┤"
        private const val RIGHT_DOWN = "┘"
        private const val DOWN = "┴"
        private const val MIDDLE = "┼"
        private const val DASH = "─"
        private const val COORDINATE_X = "%2d "
        private const val BLANK = "   "
        private const val MIN_BOUND = 1
        private const val MAX_BOUND = 15

        private const val MESSAGE_WINNER = "%s !!"
    }
}
