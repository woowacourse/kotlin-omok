package omok.view

import omok.model.Board
import omok.model.Col
import omok.model.Position
import omok.model.Row
import omok.model.Stone
import omok.model.StoneState

class OutputView {
    fun printNextTurn(board: Board) {
        board.lastStone?.let {
            val lastStoneCoordinateText = stoneCoordinateText(board.lastStone.position)
            println("${stoneStateText(board.nextStoneState)}의 차례 입니다. (마지막 돌의 위치: $lastStoneCoordinateText)")
        } ?: run {
            println("${stoneStateText(board.nextStoneState)}의 차례 입니다")
        }
    }

    fun stoneStateText(stoneState: StoneState): String =
        when (stoneState) {
            StoneState.BLACK -> "흑"
            StoneState.WHITE -> "백"
            StoneState.NONE -> ""
        }

    fun stoneCoordinateText(position: Position): String {
        val lastCol = ('A'..'O').toList()[position.col.value].toString()
        val lastRow = (position.row.value + 1).toString()

        return lastCol + lastRow
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

    fun printOmok(lastStone: Stone?) {
        val lastStoneState = stoneStateText(lastStone?.stoneState ?: StoneState.NONE)
        println("${lastStoneState}이 우승했습니다.")
    }

    fun printException(message: String?) {
        if (message == null) {
            println("알 수 없는 예외가 발생했습니다.")
        }
        println(message)
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
