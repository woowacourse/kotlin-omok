package woowacourse.omok.domain.view

import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.Player
import woowacourse.omok.domain.model.Position
import woowacourse.omok.domain.model.Stone
import woowacourse.omok.domain.model.StonePosition

class OutputView {
    fun printInitialGuide(board: Board) {
        println("오목 게임을 시작합니다.")
        printBoard(board)
    }

    fun printBoard(board: Board) {
        println()
        Position.INDEX_RANGE.forEach { row ->
            printBoardRowName(row)
            Position.INDEX_RANGE.forEach { col ->
                printBoardAxis(board, row, col)
            }
            println()
        }
        println("    A  B  C  D  E  F  G  H  I  J  K  L  M  N  O")
        println()
    }

    private fun printBoardRowName(row: Int) {
        val rowName = (Position.MAX_INDEX - row + 1).toString()
        if (rowName.length == 1) {
            print(" $rowName ")
            return
        }
        print("$rowName ")
    }

    private fun printBoardAxis(
        board: Board,
        row: Int,
        col: Int,
    ) {
        val stone = board.find(Position(row, col))
        if (row == Position.MIN_INDEX) {
            when (col) {
                Position.MIN_INDEX -> stone.printBoardSingleAxis(" ${BLACK_STONE}─", " ${WHITE_STONE}─", " ┌─")
                Position.MAX_INDEX -> stone.printBoardSingleAxis("─$BLACK_STONE ", "─$WHITE_STONE ", "─┐ ")
                else -> stone.printBoardSingleAxis("─${BLACK_STONE}─", "─${WHITE_STONE}─", "─┬─")
            }
        } else if (row == Position.MAX_INDEX) {
            when (col) {
                Position.MIN_INDEX -> stone.printBoardSingleAxis(" ${BLACK_STONE}─", " ${WHITE_STONE}─", " └─")
                Position.MAX_INDEX -> stone.printBoardSingleAxis("─$BLACK_STONE ", "─$WHITE_STONE ", "─┘ ")
                else -> stone.printBoardSingleAxis("─${BLACK_STONE}─", "─${WHITE_STONE}─", "─┴─")
            }
        } else {
            when (col) {
                Position.MIN_INDEX -> stone.printBoardSingleAxis(" ${BLACK_STONE}─", " ${WHITE_STONE}─", " ├─")
                Position.MAX_INDEX -> stone.printBoardSingleAxis("─$BLACK_STONE ", "─$WHITE_STONE ", "─┤ ")
                else -> stone.printBoardSingleAxis("─${BLACK_STONE}─", "─${WHITE_STONE}─", "─┼─")
            }
        }
    }

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

    fun printWinner(stone: Stone) {
        println("우승은 🎉${stone.output()}🎉 입니다")
    }

    fun printInvalidPosition(
        player: Player,
        position: Position,
        message: String,
    ) {
        println("${player.stone}이 둔 위치 ${position.output()}: $message")
        println()
    }

    fun printInvalidPosition(
        stonePosition: StonePosition,
        message: String,
    ) {
        println("${stonePosition.stone.output()}이 두려던 위치 ${stonePosition.position.output()}: $message")
        println()
    }

    fun printException(e: Exception) {
        println(e.message)
        println()
    }

    companion object {
        private const val BLACK_STONE = "●"
        private const val WHITE_STONE = "○"
    }
}
