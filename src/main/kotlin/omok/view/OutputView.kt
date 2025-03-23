package omok.view

import omok.model.board.Board
import omok.model.stone.Stone
import omok.model.stone.StoneState
import omok.model.stone.position.Col
import omok.model.stone.position.Position
import omok.model.stone.position.Row

class OutputView {
    fun printBoard(board: Map<Position, StoneState>) {
        for (row in BOARD_SIZE - 1 downTo 0) {
            print(String.format("%2d ", row + 1))
            val point = pointByRowIndex(row)
            val str =
                (0..<BOARD_SIZE).joinToString("──") {
                    printStone(
                        board[Position(Row(row), Col(it))] ?: StoneState.NONE,
                        point[it],
                    )
                }
            println(str)
        }
        val columnLabels = ('A'..'O').joinToString("  ")
        println("   $columnLabels")
    }

    fun printNextTurn(board: Board) {
        board.lastStone?.let { stone ->
            val lastStoneCoordinateText = stoneCoordinateText(stone.position)
            println("${stoneStateText(board.nextStoneState)}의 차례 입니다. (마지막 돌의 위치: $lastStoneCoordinateText)")
        } ?: run {
            println("${stoneStateText(board.nextStoneState)}의 차례 입니다")
        }
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

    private fun pointByRowIndex(index: Int): List<String> =
        when (index) {
            14 -> listOf("┌") + List(BOARD_SIZE - 2) { "┬" } + listOf("┐")
            0 -> listOf("└") + List(BOARD_SIZE - 2) { "┴" } + listOf("┘")
            else -> listOf("├") + List(BOARD_SIZE - 2) { "┼" } + listOf("┤")
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

    private fun stoneStateText(stoneState: StoneState): String =
        when (stoneState) {
            StoneState.BLACK -> "흑"
            StoneState.WHITE -> "백"
            StoneState.NONE -> ""
        }

    private fun stoneCoordinateText(position: Position): String {
        val lastCol = ('A'..'O').toList()[position.col.value].toString()
        val lastRow = (position.row.value + 1).toString()

        return lastCol + lastRow
    }

    companion object {
        private const val BOARD_SIZE = 15
    }
}
