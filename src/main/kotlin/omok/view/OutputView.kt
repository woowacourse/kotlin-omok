package omok.view

import omok.model.board.Board
import omok.model.board.BoardSize
import omok.model.stone.Stone
import omok.model.stone.StoneColor
import omok.model.stone.position.Col
import omok.model.stone.position.Position
import omok.model.stone.position.Row

class OutputView(
    private val boardSize: BoardSize,
) {
    private val alphabets = ('A'..'Z').toList()

    fun printBoard(board: Map<Position, StoneColor>) {
        for (row in boardSize.value - 1 downTo 0) {
            print(String.format("%2d ", row + 1))
            val point = pointByRowIndex(row)
            val str =
                (0..<boardSize.value).joinToString("──") {
                    val stoneColor = board[Position(Row(row), Col(it))]
                    getStoneText(stoneColor, point[it])
                }
            println(str)
        }
        val columnLabels = alphabets.subList(0, boardSize.value).joinToString("  ")
        println("   $columnLabels")
    }

    fun printNextTurn(board: Board) {
        board.lastStone?.let { stone ->
            val lastStoneCoordinateText = stoneCoordinateText(stone.position)
            println("${stoneColorText(board.nextStoneColor)}의 차례 입니다. (마지막 돌의 위치: $lastStoneCoordinateText)")
        } ?: run {
            println("${stoneColorText(board.nextStoneColor)}의 차례 입니다")
        }
    }

    fun printOmok(lastStone: Stone?) {
        val lastStoneColor = stoneColorText(lastStone?.stoneColor)
        println("${lastStoneColor}이 우승했습니다.")
    }

    fun printException(message: String?) {
        if (message == null) {
            println("알 수 없는 예외가 발생했습니다.")
        }
        println(message)
    }

    private fun pointByRowIndex(index: Int): List<String> =
        when (index) {
            boardSize.value - 1 -> listOf("┌") + List(boardSize.value - 2) { "┬" } + listOf("┐")
            0 -> listOf("└") + List(boardSize.value - 2) { "┴" } + listOf("┘")
            else -> listOf("├") + List(boardSize.value - 2) { "┼" } + listOf("┤")
        }

    private fun getStoneText(
        stoneColor: StoneColor?,
        noneText: String,
    ): String =
        when (stoneColor) {
            StoneColor.BLACK -> "●"
            StoneColor.WHITE -> "○"
            else -> noneText
        }

    private fun stoneColorText(stoneColor: StoneColor?): String =
        when (stoneColor) {
            StoneColor.BLACK -> "흑"
            StoneColor.WHITE -> "백"
            else -> ""
        }

    private fun stoneCoordinateText(position: Position): String {
        val lastCol = alphabets[position.col.value].toString()
        val lastRow = (position.row.value + 1).toString()

        return lastCol + lastRow
    }
}
