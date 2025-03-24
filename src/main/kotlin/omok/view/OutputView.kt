package omok.view

import omok.domain.OmokBoard
import omok.domain.OmokBoard.Companion.DEFAULT_SIZE
import omok.domain.Position
import omok.domain.StoneState
import omok.domain.turn.BlackTurn
import omok.domain.turn.Finished
import omok.domain.turn.Turn
import omok.domain.turn.WhiteTurn

class OutputView {
    fun printStartMessage() = println(MESSAGE_GAME_START)

    fun printBoardState(board: OmokBoard) {
        println()
        for (y in board.height - 1 downTo 0) {
            print(COORDINATE_X.format(y + 1))
            val row =
                (0 until board.width).map { x ->
                    if (board.getStoneState(Position(x, y)) == StoneState.BLANK) {
                        board.toUI(x, y)
                    } else {
                        board.getStoneState(Position(x, y)).UI
                    }
                }.joinToString(DASH)
            println(row)
        }
        print(BLANK)
        printCoordinateY(board.width)
    }

    fun printTurn(turn: Turn) {
        when (turn) {
            is BlackTurn -> print(MESSAGE_TURN.format(StoneState.BLACK.turn))
            is WhiteTurn -> print(MESSAGE_TURN.format(StoneState.WHITE.turn))
            is Finished -> {
                val winner =
                    when (turn.beforeTurn) {
                        StoneState.BLACK -> StoneState.BLACK.turn
                        StoneState.WHITE -> StoneState.WHITE.turn
                        StoneState.BLANK -> null
                    }
                println(MESSAGE_WINNER.format(winner))
            }
        }
    }

    fun printError(message: String) = println(message)

    companion object {
        private const val MESSAGE_GAME_START = "오목 게임을 시작합니다."
        private const val MESSAGE_TURN: String = "\n%s의 차례입니다."
        private const val DASH = "──"
        private const val COORDINATE_X = "%2d "
        private const val BLANK = "   "
        private const val MIN_BOUND = 0
        private const val MAX_BOUND = DEFAULT_SIZE - 1

        private const val MESSAGE_WINNER = "%s 승리!!"

        private val StoneState.UI: String?
            get() =
                when (this) {
                    StoneState.BLACK -> "●"
                    StoneState.WHITE -> "○"
                    StoneState.BLANK -> null
                }

        private val StoneState.turn: String?
            get() =
                when (this) {
                    StoneState.BLACK -> "흑"
                    StoneState.WHITE -> "백"
                    StoneState.BLANK -> null
                }

        private fun OmokBoard.toUI(
            x: Int,
            y: Int,
        ): String {
            return when {
                x == MIN_BOUND && y == MAX_BOUND -> "┌"
                x == MAX_BOUND && y == MAX_BOUND -> "┐"
                x == MIN_BOUND && y == MIN_BOUND -> "└"
                x == MAX_BOUND && y == MIN_BOUND -> "┘"
                x == MIN_BOUND -> "├"
                y == MAX_BOUND -> "┬"
                x == MAX_BOUND -> "┤"
                y == MIN_BOUND -> "┴"
                else -> "┼"
            }
        }

        private fun printCoordinateY(width: Int) {
            println(('A' until 'A' + width).joinToString("  "))
        }
    }
}
