package omok.view

import omok.model.Board
import omok.model.Color
import omok.model.Game

class OutputView {
    fun printOmokStart() {
        println(MESSAGE_OMOK_START)
        println()
    }

    fun printWinner(game: Game) {
        when (game.lastStone?.color) {
            Color.BLACK -> println(MESSAGE_OMOK_WINNER.format(BLACK_PLAYER))
            Color.WHITE -> println(MESSAGE_OMOK_WINNER.format(WHITE_PLAYER))
            null -> throw IllegalStateException()
        }
    }

    fun printBoard(board: Board) {
        println(updateBoardString(board))
    }

    fun updateBoardString(board: Board): String {
        val boardString: String = buildBoardString(board.row, board.col)
        val lines = boardString.lines().toMutableList()
        board.stones.forEach { stone ->
            lines[board.row - stone.position.y] = updateLineString(stone.position.x, lines[board.row - stone.position.y], stone.color)
        }
        return lines.joinToString("\n")
    }

    fun updateLineString(
        col: Int,
        line: String,
        color: Color,
    ): String {
        val x = col - 1
        val sb = StringBuilder(line)
        sb[x + ROW_NUMBER_OFFSET_SIZE + x * COLUMN_NUMBER_OFFSET_SIZE] =
            when (color) {
                Color.BLACK -> BLACK_STONE
                Color.WHITE -> WHITE_STONE
            }
        return sb.toString()
    }

    fun buildBoardString(
        height: Int,
        width: Int,
    ): String {
        val top: String = buildRow(width, BOARD_TOP_LEFT2, BOARD_TOP_MIDDLE2, BOARD_TOP_RIGHT2)
        val center: String = buildRow(width, BOARD_CENTER_LEFT2, BOARD_CENTER_MIDDLE2, BOARD_CENTER_RIGHT2)
        val bottom: String = buildRow(width, BOARD_BOTTOM_LEFT2, BOARD_BOTTOM_MIDDLE2, BOARD_BOTTOM_RIGHT2)

        val rows: List<String> = appendRows(height, top, center, bottom) + buildLastRow(height, width)
        return rows.joinToString("\n")
    }

    private fun buildRow(
        width: Int,
        left: String,
        middle: String,
        right: String,
    ): String {
        return StringBuilder().apply {
            append(left)
            repeat(width - 2) { append(middle) }
            append(right)
        }.toString()
    }

    private fun buildLastRow(
        height: Int,
        width: Int,
    ): String {
        return StringBuilder().apply {
            append(" ".repeat(height.toString().length))
            for (i in 1..width) {
                append("  ${(i + 64).toChar()}")
            }
        }.toString()
    }

    private fun appendRows(
        height: Int,
        top: String,
        center: String,
        bottom: String,
    ): List<String> {
        val rows: MutableList<StringBuilder> = MutableList(height) { StringBuilder() }
        rows.forEachIndexed { i, row ->
            row.append(" %${height.toString().length}d ".format(height - i))
            when (i) {
                0 -> row.append(top)
                height - 1 -> row.append(bottom)
                else -> row.append(center)
            }
        }
        return rows.map { row -> row.toString() }
    }

    companion object {
        private const val MESSAGE_OMOK_START = "오목 게임을 시작합니다."
        private const val MESSAGE_OMOK_WINNER = "%s이 승리했습니다!"
        private const val BLACK_PLAYER = "흑"
        private const val WHITE_PLAYER = "백"

        private const val ROW_NUMBER_OFFSET_SIZE = 4
        private const val COLUMN_NUMBER_OFFSET_SIZE = 2

        private const val BLACK_STONE = '●'
        private const val WHITE_STONE = '○'

        private const val BOARD_TOP_LEFT2 = "┌─"
        private const val BOARD_TOP_MIDDLE2 = "─┬─"
        private const val BOARD_TOP_RIGHT2 = "─┐"

        private const val BOARD_CENTER_LEFT2 = "├─"
        private const val BOARD_CENTER_MIDDLE2 = "─┼─"
        private const val BOARD_CENTER_RIGHT2 = "─┤"

        private const val BOARD_BOTTOM_LEFT2 = "└─"
        private const val BOARD_BOTTOM_MIDDLE2 = "─┴─"
        private const val BOARD_BOTTOM_RIGHT2 = "─┘"
    }
}
