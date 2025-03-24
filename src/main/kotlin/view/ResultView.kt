package view

import model.Col
import model.GameBoard
import model.Row
import model.Stone
import model.StoneColor
import view.Message.ERROR_FORMAT
import view.Message.GAME_RESULT_MESSAGE_FORMAT
import view.Message.GAME_START_MESSAGE

class ResultView {
    fun printGameStartMessage() {
        println(GAME_START_MESSAGE)
    }

    fun printGameBoard(stones: MutableList<Stone>) {
        for (row in GameBoard.ROW_RANGE.reversed()) {
            makeBoardLine(row, stones)
        }
        makeBoardColName()
    }

    private fun makeBoardColName() {
        println(
            buildString {
                append("  ")
                for (col in GameBoard.COL_RANGE) {
                    append(col.toDisplayCol())
                }
            },
        )
    }

    fun printWinner(stoneColor: StoneColor) {
        print(GAME_RESULT_MESSAGE_FORMAT.format(stoneColor.toDisplay()))
    }

    private fun makeBoardLine(
        row: Int,
        stones: MutableList<Stone>,
    ) {
        val board =
            buildString {
                append(row.toDisplayRow())
                for (col in GameBoard.COL_RANGE) {
                    append(makeBoardSquare(row, col, stones))
                }
            }
        println(board)
    }

    private fun makeBoardSquare(
        row: Int,
        col: Int,
        stones: MutableList<Stone>,
    ): String {
        val stone =
            stones.firstOrNull { it.position.row.isSame(Row.from(row)) && it.position.col.isSame(Col.from(col)) }
        if (stone != null) return stone.toEmoji()
        return when {
            row == GameBoard.ROW_RANGE.first && col == GameBoard.COL_RANGE.first -> GAME_BOARD_DOWN_LEFT_CORNER
            row == GameBoard.ROW_RANGE.first && col == GameBoard.COL_RANGE.last -> GAME_BOARD_DOWN_RIGHT_CORNER
            row == GameBoard.ROW_RANGE.last && col == GameBoard.COL_RANGE.first -> GAME_BOARD_UP_LEFT_CORNER
            row == GameBoard.ROW_RANGE.last && col == GameBoard.COL_RANGE.last -> GAME_BOARD_UP_RIGHT_CORNER
            row == GameBoard.ROW_RANGE.last -> GAME_BOARD_UP_CORNER
            row == GameBoard.ROW_RANGE.first -> GAME_BOARD_DOWN_CORNER
            col == GameBoard.COL_RANGE.first -> GAME_BOARD_LEFT_CORNER
            col == GameBoard.COL_RANGE.last -> GAME_BOARD_RIGHT_CORNER
            else -> GAME_BOARD_BASE
        }
    }

    private fun Stone.toEmoji(): String =
        when (this.color) {
            StoneColor.BLACK -> " ● "
            StoneColor.WHITE -> " ○ "
        }

    private fun Int.toDisplayRow(): String {
        if (this < 10) return " $this"
        return "$this"
    }

    private fun Int.toDisplayCol(): String = " ${(this + 64).toChar()} "

    private fun StoneColor.toDisplay(): String =
        when (this) {
            StoneColor.BLACK -> "흑"
            StoneColor.WHITE -> "백"
        }

    fun printError(message: String) {
        print(ERROR_FORMAT)
        print(message)
    }

    companion object {
        private const val GAME_BOARD_LEFT_CORNER = " ├─"
        private const val GAME_BOARD_RIGHT_CORNER = "─┤ "
        private const val GAME_BOARD_UP_CORNER = "─┬─"
        private const val GAME_BOARD_DOWN_CORNER = "─┴─"
        private const val GAME_BOARD_UP_LEFT_CORNER = " ┌─"
        private const val GAME_BOARD_DOWN_LEFT_CORNER = " └─"
        private const val GAME_BOARD_UP_RIGHT_CORNER = "─┐ "
        private const val GAME_BOARD_DOWN_RIGHT_CORNER = "─┘ "
        const val GAME_BOARD_BASE = "─┼─"
    }
}
