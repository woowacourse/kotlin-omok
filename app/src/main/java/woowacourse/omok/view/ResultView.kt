package woowacourse.omok.view

import woowacourse.omok.model.AddStoneStatus
import woowacourse.omok.model.Col
import woowacourse.omok.model.GameBoard
import woowacourse.omok.model.Row
import woowacourse.omok.model.Stone
import woowacourse.omok.model.StoneColor
import woowacourse.omok.view.Message.GAME_START_MESSAGE

interface ResultView {
    fun printStone(addStoneStatus: AddStoneStatus, stoneColor: StoneColor, position: String)

    fun printError(status: AddStoneStatus.Failed)

    fun printWinner(stoneColor: StoneColor)

    fun printGameStartMessage() {
        println(GAME_START_MESSAGE)
    }

    fun printGameBoard(stones: MutableList<Stone>) {
        for (row in GameBoard.rowRange.reversed()) {
            makeBoardLine(row, stones)
        }
        makeBoardColName()
    }

    fun printTurn(stoneColor: StoneColor)

    private fun makeBoardColName() {
        println(
            buildString {
                append("  ")
                for (col in GameBoard.colRange) {
                    append(col.toDisplayCol())
                }
            },
        )
    }


    private fun makeBoardLine(
        row: Int,
        stones: MutableList<Stone>,
    ) {
        val board =
            buildString {
                append(row.toDisplayRow())
                for (col in GameBoard.colRange) {
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
            stones.firstOrNull { it.position.row == Row.from(row) && it.position.col == Col.from(col) }
        if (stone != null) return stone.toEmoji()
        return when {
            row == GameBoard.rowRange.first && col == GameBoard.colRange.first -> GAME_BOARD_DOWN_LEFT_CORNER
            row == GameBoard.rowRange.first && col == GameBoard.colRange.last -> GAME_BOARD_DOWN_RIGHT_CORNER
            row == GameBoard.rowRange.last && col == GameBoard.colRange.first -> GAME_BOARD_UP_LEFT_CORNER
            row == GameBoard.rowRange.last && col == GameBoard.colRange.last -> GAME_BOARD_UP_RIGHT_CORNER
            row == GameBoard.rowRange.last -> GAME_BOARD_UP_CORNER
            row == GameBoard.rowRange.first -> GAME_BOARD_DOWN_CORNER
            col == GameBoard.colRange.first -> GAME_BOARD_LEFT_CORNER
            col == GameBoard.colRange.last -> GAME_BOARD_RIGHT_CORNER
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
