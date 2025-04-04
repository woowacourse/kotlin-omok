package woowacourse.omok.view.consoleView

import woowacourse.omok.model.board.Board
import woowacourse.omok.model.board.BoardSize
import woowacourse.omok.model.board.PositionStatus
import woowacourse.omok.model.board.PositionStatus.EMPTY
import woowacourse.omok.model.board.PositionStatus.OUT_OF_RANGE
import woowacourse.omok.model.board.PositionStatus.PLACED
import woowacourse.omok.model.rule.RenjuFoul
import woowacourse.omok.model.rule.RenjuFoul.FOUR_BY_FOUR_FOUL
import woowacourse.omok.model.rule.RenjuFoul.OVER_FIVE_FOUL
import woowacourse.omok.model.rule.RenjuFoul.SAFE
import woowacourse.omok.model.rule.RenjuFoul.THREE_BY_THREE_FOUL
import woowacourse.omok.model.stone.Stone
import woowacourse.omok.model.stone.StoneColor
import woowacourse.omok.model.stone.position.Col
import woowacourse.omok.model.stone.position.Position
import woowacourse.omok.model.stone.position.Row

class OutputView(
    private val boardSize: BoardSize,
) {
    private val alphabets = (START_ALPHABET..END_ALPHABET).toList()

    fun printBoard(board: Map<Position, StoneColor>) {
        for (row in boardSize.value - INDEX_ADJUSTMENT downTo 0) {
            println(rowGraphicText(row, board))
        }
        val columnLabelsGraphicText = "   " + alphabets.subList(0, boardSize.value).joinToString("  ")
        println(columnLabelsGraphicText)
    }

    private fun rowGraphicText(
        rowIndex: Int,
        board: Map<Position, StoneColor>,
    ): String =
        String.format(ROW_NUMBER_FORMAT, rowIndex + INDEX_ADJUSTMENT) +
            (0..<boardSize.value).joinToString("──") { colIndex ->
                val stoneColor = board[Position(Row(rowIndex), Col(colIndex))]
                getStoneText(stoneColor, emptyBoardPositionGraphic(rowIndex, colIndex))
            }

    private fun emptyBoardPositionGraphic(
        rowIndex: Int,
        colIndex: Int,
    ): String {
        val topRowIndex = boardSize.value - INDEX_ADJUSTMENT
        val bottomRowIndex = 0
        val innerColumnCount = boardSize.value - 2

        val rowPositionGraphics =
            when (rowIndex) {
                topRowIndex -> listOf("┌") + List(innerColumnCount) { "┬" } + listOf("┐")
                bottomRowIndex -> listOf("└") + List(innerColumnCount) { "┴" } + listOf("┘")
                else -> listOf("├") + List(innerColumnCount) { "┼" } + listOf("┤")
            }
        return rowPositionGraphics[colIndex]
    }

    private fun getStoneText(
        stoneColor: StoneColor?,
        noneText: String,
    ): String =
        when (stoneColor) {
            StoneColor.BLACK -> BLACK_STONE_GRAPHIC_TEXT
            StoneColor.WHITE -> WHITE_STONE_GRAPHIC_TEXT
            else -> noneText
        }

    fun printNextTurn(board: Board) {
        board.lastStone?.let { stone ->
            val lastStoneCoordinateText = stoneCoordinateText(stone.position)
            println(NEXT_TURN_WITH_LAST_STONE_MESSAGE.format(stoneColorText(board.nextStoneColor), lastStoneCoordinateText))
        } ?: println(NEXT_TURN_MESSAGE.format(stoneColorText(board.nextStoneColor)))
    }

    private fun stoneCoordinateText(position: Position): String {
        val lastCol = alphabets[position.col.value].toString()
        val lastRow = (position.row.value + INDEX_ADJUSTMENT).toString()

        return lastCol + lastRow
    }

    private fun stoneColorText(stoneColor: StoneColor?): String =
        when (stoneColor) {
            StoneColor.BLACK -> BLACK_STONE_KOREAN_TEXT
            StoneColor.WHITE -> WHITE_STONE_KOREAN_TEXT
            else -> ""
        }

    fun printOmok(lastStone: Stone?) {
        val lastStoneColor = stoneColorText(lastStone?.stoneColor)
        println(WIN_MESSAGE.format(lastStoneColor))
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
            PLACED -> println(ERROR_STONE_ALREADY_EXITS)
            OUT_OF_RANGE -> println(ERROR_OUT_OF_RANGE)
            EMPTY -> {}
        }
    }

    companion object {
        private const val NEXT_TURN_MESSAGE = "%s의 차례 입니다."
        private const val NEXT_TURN_WITH_LAST_STONE_MESSAGE = "%s의 차례 입니다. (마지막 돌의 위치: %s)"
        private const val WIN_MESSAGE = "%s이 우승했습니다."

        private const val ERROR_THREE_BY_THREE_FOUL = "3-3 반칙이 발생했습니다"
        private const val ERROR_FOUR_BY_FOUR_FOUL = "4-4 반칙이 발생했습니다"
        private const val ERROR_OVER_FIVE_FOUL = "장목 반칙이 발생했습니다"

        private const val ERROR_STONE_ALREADY_EXITS = "해당하는 위치에 돌이 존재합니다"
        private const val ERROR_OUT_OF_RANGE = "돌이 보드의 범위를 벗어났습니다"

        private const val BLACK_STONE_GRAPHIC_TEXT = "●"
        private const val WHITE_STONE_GRAPHIC_TEXT = "○"
        private const val BLACK_STONE_KOREAN_TEXT = "흑"
        private const val WHITE_STONE_KOREAN_TEXT = "백"
        private const val START_ALPHABET = 'A'
        private const val END_ALPHABET = 'Z'
        private const val ROW_NUMBER_FORMAT = "%2d "
        private const val INDEX_ADJUSTMENT = 1
    }
}
