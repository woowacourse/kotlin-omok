package woowacourse.omok.view

import woowacourse.omok.model.Board
import woowacourse.omok.model.Color
import woowacourse.omok.model.Game
import woowacourse.omok.model.MoveResult
import woowacourse.omok.model.position.Col

class OutputView {
    fun printOmokStart() {
        println(MESSAGE_OMOK_START)
        println()
    }

    fun printMoveResult(
        game: Game,
        moveResult: MoveResult,
    ) {
        println(
            when (moveResult) {
                is MoveResult.Success.Playing -> MESSAGE_OMOK_IN_PROGRESS
                is MoveResult.Success.Finished -> MESSAGE_OMOK_WINNER.format(moveResult.winner.toPlayerName())

                is MoveResult.Failure.PositionAlreadyOccupied -> MESSAGE_FAILURE_POSITION_ALREADY_OCCUPIED
                is MoveResult.Failure.DoubleThreeViolation -> MESSAGE_FAILURE_DOUBLE_THREE_VIOLATION
                is MoveResult.Failure.DoubleFourViolation -> MESSAGE_FAILURE_DOUBLE_FOUR_VIOLATION
                is MoveResult.Failure.OverlineViolation -> MESSAGE_FAILURE_OVERLINE_VIOLATION
                is MoveResult.Failure.StoneNotWithinColumn -> MESSAGE_FAILURE_INVALID_COL.format(game.board.col.value)
                is MoveResult.Failure.StoneNotWithinRow -> MESSAGE_FAILURE_INVALID_ROW.format(game.board.row.value)
            },
        )
    }

    fun printBoard(board: Board) {
        println(updateBoard(board))
    }

    private fun updateBoard(board: Board): String {
        val boardString: String = buildBoard(board)
        val rows = boardString.lines().toMutableList()
        board.stones.forEach { stone ->
            val y: Int = board.row.value - stone.position.y.value
            rows[y] = updateRow(rows[y], stone.position.x, stone.color)
        }
        return rows.joinToString("\n")
    }

    private fun updateRow(
        rowContent: String,
        col: Col,
        color: Color,
    ): String {
        val x: Int = col.value - 1
        val rowBuilder = StringBuilder(rowContent)
        rowBuilder[x + ROW_NUMBER_OFFSET + x * COLUMN_NUMBER_OFFSET] = color.toStoneChar()
        return rowBuilder.toString()
    }

    private fun buildBoard(board: Board): String {
        val top: String = buildRow(board, BOARD_TOP_LEFT, BOARD_TOP_MIDDLE, BOARD_TOP_RIGHT)
        val center: String =
            buildRow(board, BOARD_CENTER_LEFT, BOARD_CENTER_MIDDLE, BOARD_CENTER_RIGHT)
        val bottom: String =
            buildRow(board, BOARD_BOTTOM_LEFT, BOARD_BOTTOM_MIDDLE, BOARD_BOTTOM_RIGHT)

        val rows: List<String> = appendRows(board, top, center, bottom) + buildRowLabel(board)
        return rows.joinToString("\n")
    }

    private fun buildRow(
        board: Board,
        left: String,
        middle: String,
        right: String,
    ): String {
        return StringBuilder().apply {
            append(left)
            repeat(board.col.value - 2) { append(middle) }
            append(right)
        }.toString()
    }

    private fun buildRowLabel(board: Board): String {
        return StringBuilder().apply {
            append(" ".repeat(board.row.value.toString().length))
            for (i in 1..board.col.value) {
                append("  ${(i + ASCII_OFFSET).toChar()}")
            }
        }.toString()
    }

    private fun appendRows(
        board: Board,
        top: String,
        center: String,
        bottom: String,
    ): List<String> {
        val rows: MutableList<StringBuilder> = MutableList(board.row.value) { StringBuilder() }
        rows.forEachIndexed { i, row ->
            row.append(" %${board.row.value.toString().length}d ".format(board.row.value - i))
            when (i) {
                0 -> row.append(top)
                board.row.value - 1 -> row.append(bottom)
                else -> row.append(center)
            }
        }
        return rows.map { row -> row.toString() }
    }

    private fun Color.toPlayerName(): String {
        return when (this) {
            Color.BLACK -> BLACK_PLAYER
            Color.WHITE -> WHITE_PLAYER
        }
    }

    private fun Color.toStoneChar(): Char {
        return when (this) {
            Color.BLACK -> BLACK_STONE
            Color.WHITE -> WHITE_STONE
        }
    }

    companion object {
        private const val ROW_NUMBER_OFFSET = 4
        private const val COLUMN_NUMBER_OFFSET = 2
        private const val ASCII_OFFSET = 'A'.code - 1

        private const val MESSAGE_OMOK_START = "오목 게임을 시작합니다."
        private const val MESSAGE_OMOK_WINNER = "%s이 승리했습니다!"
        private const val MESSAGE_OMOK_IN_PROGRESS = "게임이 아직 종료되지 않았습니다."

        private const val MESSAGE_FAILURE_POSITION_ALREADY_OCCUPIED = "이미 돌이 있는 자리입니다."
        private const val MESSAGE_FAILURE_DOUBLE_THREE_VIOLATION = "삼삼 금수입니다."
        private const val MESSAGE_FAILURE_DOUBLE_FOUR_VIOLATION = "사사 금수입니다."
        private const val MESSAGE_FAILURE_OVERLINE_VIOLATION = "장목 금수입니다."
        private const val MESSAGE_FAILURE_INVALID_COL = "바둑돌은 열 1과 %s 사이에만 둘 수 있습니다."
        private const val MESSAGE_FAILURE_INVALID_ROW = "바둑돌은 행 1과 %s 사이에만 둘 수 있습니다."

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
