package omok.view

import omok.domain.OmokBoard
import omok.domain.OmokResult
import omok.domain.StoneState

class OutputView {
    fun printStartMessage() = println(MESSAGE_GAME_START)

    fun printBoardState(board: OmokBoard) {
        println()
        for (x in board.height downTo MIN_BOUND) {
            print(COORDINATE_X.format(x))
            println(
                (MIN_BOUND..board.width).joinToString(DASH) { y ->
                    board.findPoint(x, y)!!.stoneState.getDisplay ?: board.toUI(x, y)
                },
            )
        }
        print(BLANK)
        printCoordinateY(board.width)
    }

    fun printWinner(omokResult: OmokResult) {
        println(MESSAGE_WINNER.format(omokResult.toString()))
    }

    companion object {
        private const val MESSAGE_GAME_START = "오목 게임을 시작합니다."
        private const val DASH = "──"
        private const val COORDINATE_X = "%2d "
        private const val BLANK = "   "
        private const val MIN_BOUND = 1

        private const val MESSAGE_WINNER = "%s !!"

        private val StoneState.getDisplay: String?
            get() =
                when (this) {
                    StoneState.BLACK -> "●"
                    StoneState.WHITE -> "○"
                    StoneState.BLANK -> null
                }

        private fun OmokBoard.toUI(
            x: Int,
            y: Int,
        ): String {
            return when {
                x == height && y == MIN_BOUND -> "┌"
                x == height && y == width -> "┐"
                x == MIN_BOUND && y == MIN_BOUND -> "└"
                x == MIN_BOUND && y == width -> "┘"
                y == MIN_BOUND -> "├"
                x == height -> "┬"
                y == width -> "┤"
                x == MIN_BOUND -> "┴"
                else -> "┼"
            }
        }

        private fun printCoordinateY(width: Int) {
            println(('A' until 'A' + width).joinToString("  "))
        }
    }
}
