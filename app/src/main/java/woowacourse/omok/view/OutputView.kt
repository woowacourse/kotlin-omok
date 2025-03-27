package woowacourse.omok.view

import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.position.Position
import woowacourse.omok.domain.model.stone.OmokStone
import woowacourse.omok.domain.model.stone.StoneType

class OutputView {
    fun printStart() {
        println("오목 게임을 시작합니다.")
    }

    fun printTurn(stoneType: StoneType) {
        println("${stoneType.toKorean()}의 차례입니다.")
    }

    fun printLastStone(omokStone: OmokStone) {
        println("(마지막 돌의 위치: ${omokStone.position.toCoordinateString()})")
    }

    fun printBoardState(board: Board) {
        println()
        for (row in board.size downTo MIN_BOUND) {
            printRow(board, row)
        }
        print(BLANK)
        printCoordinateY(board.size)
    }

    private fun printRow(
        board: Board,
        row: Int,
    ) {
        print(COORDINATE_X.format(row))
        for (col in MIN_BOUND..board.size) {
            print(boardUI(board, row, col))
            if (col != board.size) repeat(REPEAT_COUNT) { print(DASH) }
        }
        println()
    }

    fun printResult(omokStone: OmokStone) {
        println("${omokStone.stoneType.toKorean()}의 승리입니다.")
    }

    fun printDraw() {
        println("오목판이 가득 차 무승부가 되었습니다.")
    }

    fun printErrorMessage(message: String) {
        println(message)
    }

    private fun Position.toCoordinateString(): String {
        val column = 'A' + this.column.value - 1
        val row = this.row.value
        return "${column}$row"
    }

    private fun StoneType.toKorean(): String {
        return when (this) {
            StoneType.BLACK -> "흑"
            StoneType.WHITE -> "백"
            StoneType.NONE -> ""
        }
    }

    companion object {
        private const val REPEAT_COUNT = 2
        private const val LEFT_DOWN = "└"
        private const val LEFT = "├"
        private const val LEFT_UP = "┌"
        private const val UP = "┬"
        private const val RIGHT_UP = "┐"
        private const val RIGHT = "┤"
        private const val RIGHT_DOWN = "┘"
        private const val DOWN = "┴"
        private const val MIDDLE = "┼"
        private const val DASH = "─"
        private const val COORDINATE_X = "%2d "
        private const val BLANK = "   "
        private const val MIN_BOUND = 1

        private fun boardUI(
            board: Board,
            row: Int,
            col: Int,
        ): String {
            val stone = findStoneAt(board, row, col)
            return when {
                stone != null -> stone.stoneType.toUi()
                row == board.size && col == MIN_BOUND -> LEFT_UP
                row == board.size && col == board.size -> RIGHT_UP
                row == MIN_BOUND && col == MIN_BOUND -> LEFT_DOWN
                row == MIN_BOUND && col == board.size -> RIGHT_DOWN
                col == MIN_BOUND -> LEFT
                row == board.size -> UP
                col == board.size -> RIGHT
                row == MIN_BOUND -> DOWN
                else -> MIDDLE
            }
        }

        private fun findStoneAt(
            board: Board,
            row: Int,
            col: Int,
        ): OmokStone? {
            val targetPosition = Position.of(col, row, board.size)
            return board.stones.find { it.position == targetPosition }
        }

        private fun StoneType.toUi() =
            when (this) {
                StoneType.BLACK -> "●"
                StoneType.WHITE -> "○"
                StoneType.NONE -> ""
            }

        private fun printCoordinateY(width: Int) {
            println(('A' until 'A' + width).joinToString("  "))
        }
    }
}
