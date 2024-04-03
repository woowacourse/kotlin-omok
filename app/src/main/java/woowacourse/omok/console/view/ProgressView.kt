package woowacourse.omok.console.view

import woowacourse.omok.model.board.Board
import woowacourse.omok.model.board.Position
import woowacourse.omok.model.board.Stone
import woowacourse.omok.model.game.BlackStonePlace
import woowacourse.omok.model.game.DoubleFourPlace
import woowacourse.omok.model.game.DoubleOpenThreePlace
import woowacourse.omok.model.game.DuplicationPlace
import woowacourse.omok.model.game.OverlinePlace
import woowacourse.omok.model.game.PlaceType
import woowacourse.omok.model.game.WhiteStonePlace

class ProgressView {
    fun printBoard(board: Board) {
        val boardSizeRange = 0..<board.size
        lineBreak()
        boardSizeRange.forEach { row ->
            printBoardRowName(board, row)
            boardSizeRange.forEach { col ->
                printBoardAxis(board, Position(row, col))
            }
            lineBreak()
        }
        printBoardColName(boardSizeRange)
        lineBreak()
    }

    private fun printBoardRowName(
        board: Board,
        row: Int,
    ) {
        val rowName = (board.size - row).toString()
        if (rowName.length == 1) {
            print(" $rowName ")
            return
        }
        print("$rowName ")
    }

    private fun printBoardAxis(
        board: Board,
        position: Position,
    ) {
        when (position.row) {
            0 -> board.printBoardColAxis(position, " ┌─", "─┐ ", "─┬─")
            board.size - 1 -> board.printBoardColAxis(position, " └─", "─┘ ", "─┴─")
            else -> board.printBoardColAxis(position, " ├─", "─┤ ", "─┼─")
        }
    }

    private fun Board.printBoardColAxis(
        position: Position,
        firstCol: String,
        endCol: String,
        middleCol: String,
    ) {
        val stone = find(position)
        when (position.col) {
            0 -> stone.printBoardSingleAxis(" ${BLACK_STONE}─", " ${WHITE_STONE}─", firstCol)
            size - 1 -> stone.printBoardSingleAxis("─$BLACK_STONE ", "─$WHITE_STONE ", endCol)
            else -> stone.printBoardSingleAxis("─${BLACK_STONE}─", "─${WHITE_STONE}─", middleCol)
        }
    }

    private fun lineBreak() = println()

    private fun Stone.printBoardSingleAxis(
        black: String,
        white: String,
        none: String,
    ) {
        when (this) {
            Stone.BLACK -> print(black)
            Stone.WHITE -> print(white)
            Stone.NONE -> print(none)
        }
    }

    private fun printBoardColName(boardSizeRange: IntRange) {
        println(boardSizeRange.joinToString(prefix = "    ", separator = "  ") { (it + 'A'.code).toChar().toString() })
    }

    fun printPlaceResult(placeType: PlaceType) {
        if (placeType.canPlace()) return
        println(placeType.message())
    }

    private fun PlaceType.message(): String {
        return when (this) {
            DoubleOpenThreePlace -> DOUBLE_OPEN_THREE_PLACE_MESSAGE
            DoubleFourPlace -> DOUBLE_FOUR_PLACE_MESSAGE
            OverlinePlace -> OVERLINE_PLACE_MESSAGE
            DuplicationPlace -> DUPLICATION_PLACE_MESSAGE
            BlackStonePlace,
            WhiteStonePlace,
            -> ""
        }
    }

    companion object {
        private const val BLACK_STONE = "●"
        private const val WHITE_STONE = "○"
        private const val DOUBLE_OPEN_THREE_PLACE_MESSAGE = "3–3 규칙을 위반하는 곳에는 놓을 수 없습니다."
        private const val DOUBLE_FOUR_PLACE_MESSAGE = "4–4 규칙을 위반하는 곳에는 놓을 수 없습니다."
        private const val OVERLINE_PLACE_MESSAGE = "장목 규칙을 위반하는 곳에는 놓을 수 없습니다."
        private const val DUPLICATION_PLACE_MESSAGE = "이미 돌이 놓여진 곳에는 놓을 수 없습니다."
    }
}
