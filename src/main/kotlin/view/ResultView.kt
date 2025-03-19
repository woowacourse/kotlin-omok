package view

import Col
import Row
import Stone
import StoneColor

class ResultView {
    fun printGameStartMessage() {
        println(GAME_START_MESSAGE)
    }

    fun printGameBoard(stones: MutableList<Stone>) {
        for (row in Row.MAX_VALUE.downTo(Row.MIN_VALUE)) {
            makeBoardLine(row, stones)
        }
        makeBoardColName()
    }

    private fun makeBoardColName() {
        println(
            buildString {
                append("  ")
                for (col in Col.MIN_VALUE..Col.MAX_VALUE) {
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
                for (col in Col.MIN_VALUE..Col.MAX_VALUE) {
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
            stones.firstOrNull { it.position.row.isSame(Row.from(row)) && it.position.col.isSame(Col.fromInt(col)) }
        if (stone != null) return stone.toEmoji()
        return when {
            row == Row.MIN_VALUE && col == Col.MIN_VALUE -> GAME_BOARD_DOWN_LEFT_CORNER
            row == Row.MIN_VALUE && col == Col.MAX_VALUE -> GAME_BOARD_DOWN_RIGHT_CORNER
            row == Row.MAX_VALUE && col == Col.MIN_VALUE -> GAME_BOARD_UP_LEFT_CORNER
            row == Row.MAX_VALUE && col == Col.MAX_VALUE -> GAME_BOARD_UP_RIGHT_CORNER
            row == Row.MAX_VALUE -> GAME_BOARD_UP_CORNER
            row == Row.MIN_VALUE -> GAME_BOARD_DOWN_CORNER
            col == Col.MIN_VALUE -> GAME_BOARD_LEFT_CORNER
            col == Col.MAX_VALUE -> GAME_BOARD_RIGHT_CORNER
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

    companion object {
        private const val GAME_START_MESSAGE = "오목 게임을 시작합니다."
        private const val GAME_RESULT_MESSAGE_FORMAT = "승자는 %s 입니다"
        private const val GAME_BOARD_LEFT_CORNER = " ├─"
        private const val GAME_BOARD_RIGHT_CORNER = "─┤ "
        private const val GAME_BOARD_UP_CORNER = "─┬─"
        private const val GAME_BOARD_DOWN_CORNER = "─┴─"
        private const val GAME_BOARD_UP_LEFT_CORNER = " ┌─"
        private const val GAME_BOARD_DOWN_LEFT_CORNER = " └─"
        private const val GAME_BOARD_UP_RIGHT_CORNER = "─┐ "
        private const val GAME_BOARD_DOWN_RIGHT_CORNER = "─┘ "
        private const val GAME_BOARD_BASE = "─┼─"
    }
}
