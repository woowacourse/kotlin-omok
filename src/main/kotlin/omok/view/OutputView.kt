package omok.view

import omok.domain.OmokGrid
import omok.domain.OmokResult
import omok.domain.StoneState

class OutputView {
    fun printStartMessage() = println(MESSAGE_GAME_START)

    fun printBoardState(grid: OmokGrid) {
        println()
        for (row in grid.height downTo MIN_BOUND) {
            printRow(grid, row)
        }
        print(BLANK)
        printCoordinateY(grid.width)
    }

    fun printWinner(omokResult: OmokResult) {
        println(MESSAGE_WINNER.format(omokResult.toString()))
    }

    private fun printRow(
        grid: OmokGrid,
        row: Int,
    ) {
        print(COORDINATE_X.format(row))
        for (col in MIN_BOUND..grid.width) {
            print(boardUI(grid, row, col))
            if (col != grid.width) repeat(REPEAT_COUNT) { print(DASH) }
        }
        println()
    }

    private fun boardUI(
        grid: OmokGrid,
        row: Int,
        col: Int,
    ): String {
        val state = grid.board[row][col]
        return when {
            state != StoneState.BLANK -> state.toUI()
            row == grid.height && col == MIN_BOUND -> LEFT_UP
            row == grid.height && col == grid.width -> RIGHT_UP
            row == MIN_BOUND && col == MIN_BOUND -> LEFT_DOWN
            row == MIN_BOUND && col == grid.width -> RIGHT_DOWN
            col == MIN_BOUND -> LEFT
            row == grid.height -> UP
            col == grid.width -> RIGHT
            row == MIN_BOUND -> DOWN
            else -> MIDDLE
        }
    }

    private fun StoneState.toUI(): String {
        return when (this) {
            StoneState.BLACK -> "●"
            StoneState.WHITE -> "○"
            else -> throw IllegalStateException()
        }
    }

    private fun printCoordinateY(width: Int) {
        println(('A' until 'A' + width).joinToString("  "))
    }

    companion object {
        private const val MESSAGE_GAME_START = "오목 게임을 시작합니다."
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

        private const val MESSAGE_WINNER = "%s !!"
    }
}
