package woowacourse.omok.src.main.kotlin.omok.view

import woowacourse.omok.src.main.kotlin.omok.model.board.Board
import woowacourse.omok.src.main.kotlin.omok.model.stone.GoStone
import woowacourse.omok.src.main.kotlin.omok.model.stone.StoneType

class OutputView {
    fun printStartGameComment() = println(START_GAME_MESSAGE)

    fun drawBoard() {
        // 오목판 그리기
        for (row in UPPER_LENGTH downTo 0) {
            // 왼쪽 번호
            print("${(row + 1).toString().padStart(2)} ")

            // 이제 drawRow를 각 경우에 맞게 호출
            printBoard(row)
        }
        printRowValue()
    }

    fun printWinner(stone: GoStone) {
        lineBreak()
        if (stone.stoneType == StoneType.BLACK_STONE) {
            println(BLACK_STONE_WIN_MESSAGE)
        } else {
            println(WHITE_STONE_WIN_MESSAGE)
        }
    }

    private fun printBoard(row: Int) {
        when (row) {
            UPPER_LENGTH -> {
                drawRow(row, "┌", "┬", "┐")
            }

            LOWER_ROW -> {
                drawRow(row, "└", "┴", "┘")
            }

            else -> {
                drawRow(row, "├", "┼", "┤")
            }
        }
    }

    private fun printRowValue() {
        print("   ")
        for (col in ROW_RANGE) {
            print("$col  ")
        }
        println()
    }

    private fun drawRow(
        row: Int,
        firstSymbol: String,
        middleSymbol: String,
        lastSymbol: String,
    ) {
        printSymbol(row, firstSymbol)
        for (column in 1 until UPPER_LENGTH) {
            val stone = Board.getStoneType(row, column)
            print(if (stone == StoneType.NONE) "──$middleSymbol" else "──${stone.value()}")
        }
        printLastSymbol(row, lastSymbol)
        println()
    }

    private fun printLastSymbol(
        row: Int,
        lastSymbol: String,
    ) {
        val stone = Board.getStoneType(row, UPPER_LENGTH)
        if (stone != StoneType.NONE) {
            print("──${stone.value()}")
        } else {
            print("──$lastSymbol")
        }
    }

    private fun printSymbol(
        row: Int,
        firstSymbol: String,
    ) {
        if (Board.getStoneType(row, MIN_COL) != StoneType.NONE) {
            print(Board.getStoneType(row, MIN_COL).value())
        } else if (Board.getStoneType(row, UPPER_LENGTH) != StoneType.NONE) {
            print(Board.getStoneType(row, UPPER_LENGTH).value())
        } else {
            print(firstSymbol)
        }
    }

    private fun StoneType.value() =
        when (this) {
            StoneType.BLACK_STONE -> "●"
            StoneType.WHITE_STONE -> "○"
            StoneType.NONE -> ""
        }

    private fun lineBreak() = println()

    companion object {
        private const val UPPER_LENGTH = 14
        private const val LOWER_ROW = 0
        private const val ROW_START = 'A'
        private const val ROW_END = 'O'
        private const val MIN_COL = 0
        private val ROW_RANGE = ROW_START..ROW_END
        private const val START_GAME_MESSAGE = "오목 게임을 시작합니다."
        private const val BLACK_STONE_WIN_MESSAGE = "흑의 승리입니다."
        private const val WHITE_STONE_WIN_MESSAGE = "백의 승리입니다."
    }
}
