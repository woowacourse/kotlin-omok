package omok.view

import omok.domain.OmokGrid
import omok.domain.OmokGrid.Companion.DEFAULT_SIZE
import omok.domain.OmokResult
import omok.domain.StoneState

class OutputView {
    fun printErrorMessage(message: String?) {
        if (message == null) {
            println(ERROR_NOT_FOUND)
        } else {
            println(ERROR_MESSAGE.format(message))
        }
    }

    private fun makeBoard(grid: OmokGrid): List<List<StoneState?>> {
        val board: List<MutableList<StoneState?>> = List(DEFAULT_SIZE + 1) { MutableList(DEFAULT_SIZE + 1) { null } }

        grid.blackStones.stones.forEach { (row, col) ->
            board[row.value][col.value] = StoneState.BLACK
        }

        grid.whiteStones.stones.forEach { (row, col) ->
            board[row.value][col.value] = StoneState.WHITE
        }
        return board
    }

    fun printStartMessage() = println(MESSAGE_GAME_START)

    fun printBoardState(grid: OmokGrid) {
        val board = makeBoard(grid)
        println()
        for (row in DEFAULT_SIZE downTo MIN_BOUND) {
            printRow(board, row)
        }
        print(BLANK)
        printCoordinateY()
    }

    fun printWinner(omokResult: OmokResult) {
        println(MESSAGE_WINNER.format(omokResult.toString()))
    }

    private fun printRow(
        grid: List<List<StoneState?>>,
        row: Int,
    ) {
        print(COORDINATE_X.format(row))
        for (col in MIN_BOUND..DEFAULT_SIZE) {
            print(boardUI(grid, row, col))
            if (col != DEFAULT_SIZE) repeat(REPEAT_COUNT) { print(DASH) }
        }
        println()
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

        private fun boardUI(
            grid: List<List<StoneState?>>,
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

        private fun StoneState.toUI(): String {
            return when (this) {
                StoneState.BLACK -> "●"
                StoneState.WHITE -> "○"
            }
        }

        private fun printCoordinateY() {
            println(('A' until 'A' + DEFAULT_SIZE).joinToString("  "))
        }
    }
}
