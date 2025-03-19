package omok.view

import omok.model.Board
import omok.model.Col
import omok.model.Position
import omok.model.Row
import omok.model.StoneState

class OutputView {
    fun printNextTurn(board: Board) {
        val stoneColor =
            when (board.lastStoneState) {
                StoneState.BLACK -> "백"
                StoneState.WHITE -> "흑"
                else -> ""
            }
        println("${stoneColor}의 차례 입니다.")
    }

    private fun pointByRowIndex(index: Int): List<String> =
        when (index) {
            14 -> listOf("┌") + List(BOARD_SIZE - 2) { "┬" } + listOf("┐")
            0 -> listOf("└") + List(BOARD_SIZE - 2) { "┴" } + listOf("┘")
            else -> listOf("├") + List(BOARD_SIZE - 2) { "┼" } + listOf("┤")
        }

    fun printBoard(board: Map<Position, StoneState>) {
        for (row in BOARD_SIZE - 1 downTo 0) {
            print(String.format("%2d ", row + 1))
            val point = pointByRowIndex(row)
            val str =
                (0..<BOARD_SIZE)
                    .map {
                        printStone(
                            board[Position(Row(row), Col(it))] ?: StoneState.NONE,
                            point[it],
                        )
                    }.joinToString(OMOK_SEPARATOR)
            println(str)
        }
        val columnLabels = ('A'..'O').joinToString("  ")
        println("   $columnLabels")
    }

    private fun printStone(
        stoneState: StoneState,
        nonString: String,
    ): String =
        when (stoneState) {
            StoneState.BLACK -> "●"
            StoneState.WHITE -> "○"
            else -> nonString
        }

    companion object {
        private const val OMOK_SEPARATOR = "──"
        private const val TOP_START = "┌"
        private const val TOP_END = "┐"
        private const val TOP_MIDDLE_STRING = "┬"
        private const val MIDDLE_START = "├"
        private const val MIDDLE_END = "┤"
        private const val MIDDLE_MIDDLE_STRING = "┼"
        private const val BOTTOM_START = "└"
        private const val BOTTOM_END = "┘"
        private const val BOTTOM_MIDDLE_STRING = "┴"
        private const val BOARD_SIZE = 15
    }
}
