package omok.view

import omok.model.Board
import omok.model.Color
import omok.model.MoveResult

class OutputView {
    fun printOmokStart() {
        println(MESSAGE_OMOK_START)
        println()
    }

    fun printMoveResult(moveResult: MoveResult) {
        when (moveResult) {
            is MoveResult.Success.Playing -> MESSAGE_OMOK_IN_PROGRESS
            is MoveResult.Success.BlackWin -> println(MESSAGE_OMOK_WINNER.format(BLACK_PLAYER))
            is MoveResult.Success.WhiteWin -> println(MESSAGE_OMOK_WINNER.format(WHITE_PLAYER))
            is MoveResult.Fail.StoneAlreadyPlaced -> println(FAILURE_POSITION_ALREADY_OCCUPIED)
            is MoveResult.Fail.DoubleThreeViolation -> println(FAILURE_DOUBLE_THREE_VIOLATION)
            is MoveResult.Fail.DoubleFourViolation -> println(FAILURE_DOUBLE_FOUR_VIOLATION)
            is MoveResult.Fail.OverlineViolation -> println(FAILURE_OVERLINE_VIOLATION)
        }
    }

    fun printBoard(board: Board) {
        println(updateBoard(board))
    }

    private fun updateBoard(board: Board): String {
        val boardString: String = buildBoard(board.row, board.col)
        val lines = boardString.lines().toMutableList()
        board.stones.forEach { stone ->
            lines[board.row - stone.position.y] = updateRow(stone.position.x, lines[board.row - stone.position.y], stone.color)
        }
        return lines.joinToString("\n")
    }

    private fun updateRow(
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

    private fun buildBoard(
        height: Int,
        width: Int,
    ): String {
        val top: String = buildRow(width, BOARD_TOP_LEFT, BOARD_TOP_MIDDLE, BOARD_TOP_RIGHT)
        val center: String = buildRow(width, BOARD_CENTER_LEFT, BOARD_CENTER_MIDDLE, BOARD_CENTER_RIGHT)
        val bottom: String = buildRow(width, BOARD_BOTTOM_LEFT, BOARD_BOTTOM_MIDDLE, BOARD_BOTTOM_RIGHT)

        val rows: List<String> = appendRows(height, top, center, bottom) + buildRowLabel(height, width)
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

    private fun buildRowLabel(
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
        private const val ROW_NUMBER_OFFSET_SIZE = 4
        private const val COLUMN_NUMBER_OFFSET_SIZE = 2

        private const val MESSAGE_OMOK_START = "오목 게임을 시작합니다."
        private const val MESSAGE_OMOK_WINNER = "%s이 승리했습니다!"
        private const val MESSAGE_OMOK_IN_PROGRESS = "게임이 아직 종료되지 않았습니다."

        private const val FAILURE_POSITION_ALREADY_OCCUPIED = "이미 돌이 있는 자리입니다."
        private const val FAILURE_DOUBLE_THREE_VIOLATION = "삼삼 금수입니다."
        private const val FAILURE_DOUBLE_FOUR_VIOLATION = "사사 금수입니다."
        private const val FAILURE_OVERLINE_VIOLATION = "장목 금수입니다."

        private const val BLACK_PLAYER = "흑"
        private const val WHITE_PLAYER = "백"
        private const val BLACK_STONE = '●'
        private const val WHITE_STONE = '○'

        private const val BOARD_TOP_LEFT = "┌─"
        private const val BOARD_TOP_MIDDLE = "─┬─"
        private const val BOARD_TOP_RIGHT = "─┐"

        private const val BOARD_CENTER_LEFT = "├─"
        private const val BOARD_CENTER_MIDDLE = "─┼─"
        private const val BOARD_CENTER_RIGHT = "─┤"

        private const val BOARD_BOTTOM_LEFT = "└─"
        private const val BOARD_BOTTOM_MIDDLE = "─┴─"
        private const val BOARD_BOTTOM_RIGHT = "─┘"
    }
}
