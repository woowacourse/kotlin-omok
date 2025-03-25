package omok.view

import omok.model.board.Board
import omok.model.stone.Stone
import omok.model.stone.StoneColor
import omok.model.stone.position.Col
import omok.model.stone.position.Position
import omok.model.stone.position.Row

class OutputView {
    fun printBoard(board: Board) {
        val boardWidth = board.getWidth()
        val boardHeight = board.getHeight()

        for (row in boardHeight - 1 downTo 0) {
            print(String.format("%2d ", row + 1))
            val point = pointByRowIndex(row, boardHeight, boardWidth)
            val str =
                (0 until boardWidth).joinToString("──") { col ->
                    val color: StoneColor? = board.stonesMap[Position(Row(row), Col(col))]
                    printStone(color, point[col])
                }
            println(str)
        }
        val columnLabels = (MIN_COL_CHAR until (MIN_COL_CHAR + boardWidth)).joinToString("  ")
        println("   $columnLabels")
    }

    fun printNextTurn(
        turn: StoneColor,
        lastStone: Stone?,
    ) {
        lastStone?.let {
            val coordinatePosition = stoneCoordinateText(it.position)
            println("${stoneStateText(turn)}의 차례입니다. (마지막 돌의 위치: $coordinatePosition)")
        } ?: run {
            println("${stoneStateText(turn)}의 차례입니다")
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

    private fun pointByRowIndex(
        index: Int,
        boardHeight: Int,
        boardWidth: Int,
    ): List<String> =
        when (index) {
            boardHeight - 1 -> listOf("┌") + List(boardWidth - 2) { "┬" } + listOf("┐")
            0 -> listOf("└") + List(boardWidth - 2) { "┴" } + listOf("┘")
            else -> listOf("├") + List(boardWidth - 2) { "┼" } + listOf("┤")
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
        val lastCol = (MIN_COL_CHAR + position.col.value)
        val lastRow = (position.row.value + 1).toString()

        return lastCol + lastRow
    }

    companion object {
        private const val MIN_COL_CHAR = 'A'
    }
}
