package omok.view

import omok.domain.OmokBoard
import omok.domain.OmokResult
import omok.domain.StoneState

class OutputView {
    fun printStartMessage() = println(MESSAGE_GAME_START)

    fun printBoardState(board: OmokBoard) {
        println()
        for (y in board.height downTo MIN_BOUND) {
            print(COORDINATE_X.format(y))
            println((MIN_BOUND..board.width).joinToString(DASH) { x -> board.toUI(x, y) })
        }
        print(BLANK)
        printCoordinateY(board.width)
    }

    fun printWinner(omokResult: OmokResult) {
        println(MESSAGE_WINNER.format(omokResult.toString()))
    }

    companion object {
        private const val MESSAGE_GAME_START = "오목 게임을 시작합니다."

        private const val LEFT_DOWN = "└"
        private const val LEFT = "├"
        private const val LEFT_UP = "┌"
        private const val UP = "┬"
        private const val RIGHT_UP = "┐"
        private const val RIGHT = "┤"
        private const val RIGHT_DOWN = "┘"
        private const val DOWN = "┴"
        private const val MIDDLE = "┼"
        private const val DASH = "──"
        private const val COORDINATE_X = "%2d "
        private const val BLANK = "   "
        private const val MIN_BOUND = 1

        private const val MESSAGE_WINNER = "%s !!"

        private fun StoneState.toUI(): String? {
            return when (this) {
                StoneState.BLACK -> "●"
                StoneState.WHITE -> "○"
                StoneState.BLANK -> null
            }
        }

        private fun OmokBoard.toUI(
            x: Int,
            y: Int,
        ): String {
            return when {
                y == height && x == MIN_BOUND -> LEFT_UP
                y == height && x == width -> RIGHT_UP
                y == MIN_BOUND && x == MIN_BOUND -> LEFT_DOWN
                y == MIN_BOUND && x == width -> RIGHT_DOWN
                x == MIN_BOUND -> LEFT
                y == height -> UP
                x == width -> RIGHT
                y == MIN_BOUND -> DOWN
                else -> MIDDLE
            }
        }

        private fun printCoordinateY(width: Int) {
            println(('A' until 'A' + width).joinToString("  "))
        }
    }
}
