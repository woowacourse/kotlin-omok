package omok.view

import omok.model.board.Board
import omok.model.stone.Stone
import omok.model.stone.StoneColor
import omok.model.stone.position.Col
import omok.model.stone.position.Position
import omok.model.stone.position.Row

class OutputView {
    fun printBoard(board: Map<Position, StoneColor>) {
        for (row in BOARD_SIZE - 1 downTo 0) {
            print(String.format("%2d ", row + 1))
            val point = pointByRowIndex(row)
            val str =
                (0 until BOARD_SIZE).joinToString("──") { col ->
                    val color: StoneColor? = board[Position(Row(row), Col(col))]
                    printStone(color, point[col])
                }
            println(str)
        }
        val columnLabels = (MIN_COL_CHAR until (MIN_COL_CHAR + BOARD_SIZE)).joinToString("  ")
        println("   $columnLabels")
    }

    fun printNextTurn(board: Board) {
        board.lastStone?.let {
            val lastStoneCoordinateText = stoneCoordinateText(board.lastStone.position)
            println("${stoneStateText(board.nextStoneColor)}의 차례 입니다. (마지막 돌의 위치: $lastStoneCoordinateText)")
        } ?: run {
            println("${stoneStateText(board.nextStoneColor)}의 차례 입니다")
        }
    }

    fun printOmok(lastStone: Stone?) {
        val winner = lastStone?.stoneColor ?: return
        println("${stoneStateText(winner)}이 우승했습니다.")
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
        stoneColor: StoneColor?,
        nonString: String,
    ): String =
        when (stoneColor) {
            StoneColor.BLACK -> "●"
            StoneColor.WHITE -> "○"
            null -> nonString
        }

    private fun stoneStateText(stoneColor: StoneColor): String =
        when (stoneColor) {
            StoneColor.BLACK -> "흑"
            StoneColor.WHITE -> "백"
        }

    private fun stoneCoordinateText(position: Position): String {
        val lastCol = (MIN_COL_CHAR..<MIN_COL_CHAR + BOARD_SIZE).toList()[position.col.value].toString()
        val lastRow = (position.row.value + 1).toString()

        return lastCol + lastRow
    }

    companion object {
        const val BOARD_SIZE = 15
        private const val MIN_COL_CHAR = 'A'
    }
}
