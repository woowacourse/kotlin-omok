package woowacourse.omok.view

import woowacourse.omok.model.board.Board
import woowacourse.omok.model.board.Position
import woowacourse.omok.model.board.Stone
import woowacourse.omok.model.game.PlaceType

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
        if (position.row == 0) {
            board.printBoardColAxis(position, " ┌─", "─┐ ", "─┬─")
        } else if (position.row == board.size - 1) {
            board.printBoardColAxis(position, " └─", "─┘ ", "─┴─")
        } else {
            board.printBoardColAxis(position, " ├─", "─┤ ", "─┼─")
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
        if (placeType != PlaceType.CANNOT_PLACE) return
        println(CANNOT_PLACE_POSITION_MESSAGE)
    }

    companion object {
        private const val BLACK_STONE = "●"
        private const val WHITE_STONE = "○"
        private const val CANNOT_PLACE_POSITION_MESSAGE = "놓을 수 없는 위치입니다."
    }
}
