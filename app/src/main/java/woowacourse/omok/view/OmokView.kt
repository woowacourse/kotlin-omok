package woowacourse.omok.view

import woowacourse.omok.model.board.Position
import woowacourse.omok.model.board.PositionState
import woowacourse.omok.model.board.X
import woowacourse.omok.model.board.Y
import woowacourse.omok.model.stone.Stone
import woowacourse.omok.model.stone.StoneColor

class OmokView {
    fun printForbiddenMessage() {
        println("금수 자리입니다. 다시 선택해주세요.")
    }

    fun printStartMessage() {
        println(START_MESSAGE)
        printOmokBoard()
    }

    private fun String.toNumber(): Int {
        val alphaBets = ('A'..'O').toList()
        return alphaBets.indexOfFirst { it.toString() == this } + 1
    }

    fun inputPosition(stone: Stone): Position {
        println("${changeName(stone)}의 차례입니다.")
        print("위치를 입력하세요: ")
        val input = readln().trim()
        val (alphaBet, number) = input.partition { it.isLetter() }
        return Position(X(alphaBet.uppercase().toNumber()), Y(number.toInt()))
    }

    private fun printOmokBoard() {
        val row = List(BOARD_SIZE - 2) { "──┼" }
        val column = ('A'..'O').toList()
        println("15 ┌${"──┬".repeat(BOARD_SIZE - 2)}──┐")
        for (i in 14 downTo 10) {
            println("$i ├${row.joinToString("")}──┤")
        }
        for (i in 9 downTo 2) {
            println("$i  ├${row.joinToString("")}──┤")
        }
        println("1  └${"──┴".repeat(BOARD_SIZE - 2)}──┘")
        println("   ${column.joinToString("  ")}")
    }

    fun printOmokBoard(positions: Map<Position, PositionState>) {
        val column = ('A'..'O').toList()
        val board = displayBoard()
        displayCorner(board)
        displayBorder(board)
        renderStone(positions, board)
        disPlayOmokBoard(board, column)
    }

    private fun disPlayOmokBoard(
        board: MutableList<MutableList<String>>,
        column: List<Char>,
    ) {
        for (y in BOARD_SIZE downTo 1) {
            val space = if (y > 9) " " else "  "
            print("$y$space")
            for (x in 1..BOARD_SIZE) {
                print(board[y - 1][x - 1])
            }
            println()
        }
        println("   ${column.joinToString("  ")}")
    }

    private fun renderStone(
        positions: Map<Position, PositionState>,
        board: MutableList<MutableList<String>>,
    ) {
        positions.forEach { (pos, stoneState) ->
            val stone =
                when (stoneState) {
                    PositionState.WHITE_POSITION -> WHITE_STONE
                    PositionState.BLACK_POSITION -> BLACK_STONE
                    PositionState.FORBIDDEN -> ILLEGAL_POINT
                    else -> return@forEach
                }
            board[pos.y.point - 1][pos.x.point - 1] =
                if (pos.x.point != BOARD_SIZE) "$stone──" else stone
        }
    }

    private fun displayBorder(board: MutableList<MutableList<String>>) {
        repeat(BOARD_SIZE - 2) { i ->
            val index = i + 1
            board[0][index] = "┴──"
            board[BOARD_SIZE - 1][index] = "┬──"
            board[index][0] = "├──"
            board[index][BOARD_SIZE - 1] = "┤"
        }
    }

    private fun displayBoard(): MutableList<MutableList<String>> =
        MutableList(BOARD_SIZE) {
            MutableList(BOARD_SIZE) { x -> if (x == BOARD_SIZE - 1) "┼" else "┼──" }
        }

    private fun displayCorner(board: MutableList<MutableList<String>>) {
        board[0][0] = "└──"
        board[BOARD_SIZE - 1][0] = "┌──"
        board[0][BOARD_SIZE - 1] = "┘"
        board[BOARD_SIZE - 1][BOARD_SIZE - 1] = "┐"
    }

    fun result(stone: Stone) {
        println("${changeName(stone)}이 승리했습니다.")
    }

    private fun changeName(stone: Stone): String =
        when (stone.color) {
            StoneColor.BLACK -> "흑"
            StoneColor.WHITE -> "백"
        }

    companion object {
        private const val BOARD_SIZE = 15
        private const val BLACK_STONE = "●"
        private const val WHITE_STONE = "○"
        private const val ILLEGAL_POINT = "X"
        private const val START_MESSAGE = "오목 게임을 시작합니다."
    }
}
