package view

import woowacourse.omok.domain.position.Col
import woowacourse.omok.domain.position.Position
import woowacourse.omok.domain.position.Row
import woowacourse.omok.domain.stone.Stone
import woowacourse.omok.domain.stone.StoneColor

class ResultView {
    fun printGameStartMessage() {
        println(GAME_START_MESSAGE)
    }

    fun printGameBoard(stones: List<Stone> = emptyList()) {
        for (row in Row.MAX_VALUE downTo Row.MIN_VALUE) {
            printBoardRowStatus(row, stones)
        }

        printBoardColName()
    }

    private fun printBoardRowStatus(
        row: Int,
        stones: List<Stone>,
    ) {
        println(
            buildString {
                append(row.toDisplayRow())
                for (col in Col.MIN_VALUE..Col.MAX_VALUE) {
                    append(makeBoardSquare(row, col, stones))
                }
            },
        )
    }

    private fun makeBoardSquare(
        row: Int,
        col: Int,
        stones: List<Stone>,
    ): String =
        stones.firstOrNull { stone -> stone.position.isSame(Position(Row.from(row), Col.from(col))) }?.toEmoji()
            ?: toBoardDisplay(row, col)

    private fun printBoardColName() {
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

    fun printErrorMessage(error: Throwable) {
        println(ERROR_MESSAGE_FORMAT.format(error.message ?: ""))
    }

    private fun toBoardDisplay(
        row: Int,
        col: Int,
    ): String =
        when {
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

    private fun Stone.toEmoji(): String =
        when (this.color) {
            woowacourse.omok.domain.stone.StoneColor.BLACK -> " ● "
            woowacourse.omok.domain.stone.StoneColor.WHITE -> " ○ "
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
        private const val ERROR_MESSAGE_FORMAT = "[ERROR] %s"
    }
}
