package omok.view

import omok.domain.OmokGame.Companion.MAX_BOUND
import omok.domain.OmokGame.Companion.MIN_BOUND
import omok.domain.OmokResult
import omok.domain.StoneState

class OutputView {
    fun printStartMessage() = println(MESSAGE_GAME_START)

    fun printBoardState(board: List<List<StoneState>>) {
        for (row in MAX_BOUND downTo MIN_BOUND) {
            printRow(board[row], row)
        }
        println(COORDINATE_Y)
    }

    fun printWinner(omokResult: OmokResult) {
        println(MESSAGE_WINNER.format(omokResult.toString()))
    }

    private fun printRow(
        boardRow: List<StoneState>,
        row: Int,
    ) {
        print(COORDINATE_X.format(row + 1))
        for (col in MIN_BOUND..MAX_BOUND) {
            print(boardUI(boardRow[col], row, col))
            if (col != MAX_BOUND) repeat(REPEAT_COUNT) { print(DASH) }
        }
        println()
    }

    private fun boardUI(
        state: StoneState,
        row: Int,
        col: Int,
    ): String {
        return when {
            state != StoneState.BLANK -> state.toUI()
            row == MAX_BOUND && col == MIN_BOUND -> LEFT_UP
            row == MAX_BOUND && col == MAX_BOUND -> RIGHT_UP
            row == MIN_BOUND && col == MIN_BOUND -> LEFT_DOWN
            row == MIN_BOUND && col == MAX_BOUND -> RIGHT_DOWN
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
            else -> throw IllegalStateException()
        }
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
        private const val COORDINATE_Y = "   A  B  C  D  E  F  G  H  I  J  K  L  M  N  O"

        private const val MESSAGE_WINNER = "%s !!"
    }
}
