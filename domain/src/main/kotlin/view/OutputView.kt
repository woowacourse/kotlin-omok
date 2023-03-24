package view

import domain.stone.Color
import domain.stone.Column
import domain.stone.Position
import domain.stone.Row

class OutputView {
    fun printGameStartMessage() {
        println(GAME_START_MESSAGE)
    }

    fun printOmokBoardState(board: Map<Position, Color?>) {
        (ROW_START..ROW_SIZE).reversed().forEach { rowNumber ->
            when (rowNumber) {
                ROW_SIZE -> println(rowString(board, rowNumber, "┌", "┬", "┐"))
                ROW_START -> println(rowString(board, rowNumber, "└", "┴", "┘"))
                else -> println(rowString(board, rowNumber, "├", "┼", "┤"))
            }
        }
        println(columnString())
    }

    private fun rowString(board: Map<Position, Color?>, rowNumber: Int, start: String, center: String, last: String) =
        buildString {
            append(ROW_NUMBER.format(rowNumber))
            val leftPosition = board[Position(Column.valueOf(COLUMN_START - 1), Row.valueOf(rowNumber - 1))]
            when (leftPosition) {
                Color.BLACK -> append(BLACK_STONE)
                Color.WHITE -> append(WHITE_STONE)
                else -> append(start)
            }

            (2..COLUMN_SIZE - 1).forEach { columnNumber ->
                append("──")
                val position = board[Position(Column.valueOf(columnNumber - 1), Row.valueOf(rowNumber - 1))]
                when (position) {
                    Color.BLACK -> append(BLACK_STONE)
                    Color.WHITE -> append(WHITE_STONE)
                    else -> append(center)
                }
            }
            append("──")
            val rightPosition = board[Position(Column.valueOf(COLUMN_SIZE - 1), Row.valueOf(rowNumber - 1))]
            when (rightPosition) {
                Color.BLACK -> append(BLACK_STONE)
                Color.WHITE -> append(WHITE_STONE)
                else -> append(last)
            }
        }

    private fun columnString(): String = buildString {
        append("   ")
        Column.values().forEach {
            append("$it  ")
        }
    }

    fun printWinner(color: Color) {
        println(WINNER.format(color.korean))
    }

    companion object {
        private val COLUMN_SIZE = Column.values().size
        private val ROW_SIZE = Row.values().size
        private const val COLUMN_START = 1
        private const val ROW_START = 1
        private const val BLACK_STONE = "●"
        private const val WHITE_STONE = "○"

        private const val GAME_START_MESSAGE = "오목 게임을 시작합니다."
        private const val WINNER = "승리자: %s"

        private const val ROW_NUMBER = "%2d "
    }
}
