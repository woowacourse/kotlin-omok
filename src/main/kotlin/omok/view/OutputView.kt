package omok.view

import omok.domain.model.Board
import omok.domain.model.position.Column
import omok.domain.model.position.Position
import omok.domain.model.position.Row
import omok.domain.model.stone.StoneType

class OutputView {
    fun printStart() {
        println("오목 게임을 시작합니다.")
    }

    fun printTurn(omokStoneUiModel: OmokStoneUiModel) {
        println("${omokStoneUiModel.stone}의 차례입니다. (마지막 돌의 위치: ${omokStoneUiModel.position}")
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
            val stone = board.stones[Position(Column.from(col.toAlphabet()), Row(row))]
            return when {
                stone != null -> stone.toUi()
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

        private fun StoneType.toUi() =
            when (this) {
                StoneType.BLACK -> "●"
                StoneType.WHITE -> "○"
            }

        private fun printCoordinateY(width: Int) {
            println(('A' until 'A' + width).joinToString("  "))
        }

        private fun Int.toAlphabet(): Char = ('A'..'O').toList()[this - 1]
    }
}
