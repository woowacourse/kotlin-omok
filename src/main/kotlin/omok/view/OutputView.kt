package omok.view

import omok.model.board.Board
import omok.model.board.BoardSize
import omok.model.board.PositionStatus
import omok.model.board.PositionStatus.EMPTY
import omok.model.board.PositionStatus.OUT_OF_RANGE
import omok.model.board.PositionStatus.STONE_ALREADY_EXITS
import omok.model.rule.RenjuFoul
import omok.model.rule.RenjuFoul.FOUR_BY_FOUR_FOUL
import omok.model.rule.RenjuFoul.OVER_FIVE_FOUL
import omok.model.rule.RenjuFoul.SAFE
import omok.model.rule.RenjuFoul.THREE_BY_THREE_FOUL
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

    fun printNextTurn(board: Board) {
        board.lastStone?.let { stone ->
            val lastStoneCoordinateText = stoneCoordinateText(stone.position)
            println("${stoneColorText(board.nextStoneColor)}의 차례 입니다. (마지막 돌의 위치: $lastStoneCoordinateText)")
        } ?: run {
            println("${stoneColorText(board.nextStoneColor)}의 차례 입니다")
        }
    }

    private fun stoneCoordinateText(position: Position): String {
        val lastCol = alphabets[position.col.value].toString()
        val lastRow = (position.row.value + 1).toString()

        return lastCol + lastRow
    }

    private fun stoneColorText(stoneColor: StoneColor?): String =
        when (stoneColor) {
            StoneColor.BLACK -> "흑"
            StoneColor.WHITE -> "백"
            else -> ""
        }

    fun printOmok(lastStone: Stone?) {
        val lastStoneColor = stoneColorText(lastStone?.stoneColor)
        println("${lastStoneColor}이 우승했습니다.")
    }

    fun printFoul(foul: RenjuFoul) {
        when (foul) {
            THREE_BY_THREE_FOUL -> println(ERROR_THREE_BY_THREE_FOUL)
            FOUR_BY_FOUR_FOUL -> println(ERROR_FOUR_BY_FOUR_FOUL)
            OVER_FIVE_FOUL -> println(ERROR_OVER_FIVE_FOUL)
            SAFE -> {}
        }
    }

    fun printPositionStatus(positionState: PositionStatus) {
        when (positionState) {
            STONE_ALREADY_EXITS -> println(ERROR_STONE_ALREADY_EXITS)
            OUT_OF_RANGE -> println(ERROR_OUT_OF_RANGE)
            EMPTY -> {}
        }
    }

    companion object {
        private const val ERROR_THREE_BY_THREE_FOUL = "3-3 반칙이 발생했습니다"
        private const val ERROR_FOUR_BY_FOUR_FOUL = "4-4 반칙이 발생했습니다"
        private const val ERROR_OVER_FIVE_FOUL = "장목 반칙이 발생했습니다"

        private const val ERROR_STONE_ALREADY_EXITS = "해당하는 위치에 돌이 존재합니다"
        private const val ERROR_OUT_OF_RANGE = "돌이 보드의 범위를 벗어났습니다"
    }
}
