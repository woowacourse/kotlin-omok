package omok.view

import woowacourse.omok.model.board.Position
import woowacourse.omok.model.player.BlackPlayerState
import woowacourse.omok.model.player.PlayerState
import woowacourse.omok.model.player.WhitePlayerState
import woowacourse.omok.model.stone.StoneState

class OmokView {
    fun printStartMessage() {
        println(START_MESSAGE)
        printBoard()
    }

    private fun String.toNumber(): Int {
        val alphaBets = ('A'..'O').toList()
        return alphaBets.indexOfFirst { it.toString() == this } + 1
    }

    fun inputPosition(playerState: PlayerState): Position {
        println("${changeName(playerState)}의 차례입니다.")
        print("위치를 입력하세요: ")
        val input = readln().trim()
        val (alphaBet, number) = input.partition { it.isLetter() }
        return Position.from(alphaBet.uppercase().toNumber(), number.toInt())
    }

    private fun printBoard() {
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

    fun printBoard(positions: Map<Position, StoneState>) {
        val column = ('A'..'O').toList()
        val board = displayBoard()
        displayCorner(board)
        displayBorder(board)
        renderStone(positions, board)
        disPlayBoard(board, column)
    }

    private fun disPlayBoard(
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
        positions: Map<Position, StoneState>,
        board: MutableList<MutableList<String>>,
    ) {
        positions.forEach { (pos, stoneState) ->
            val stone =
                when (stoneState) {
                    StoneState.WHITE -> WHITE_STONE
                    StoneState.BLACK -> BLACK_STONE
                    else -> return@forEach
                }
            board[pos.y.value - 1][pos.x.value - 1] = if (pos.x.value != BOARD_SIZE) "$stone──" else stone
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

    fun result(stoneState: StoneState) {
        println("${changeWinner(stoneState)}이 승리했습니다.")
    }

    private fun changeName(playerState: PlayerState): String =
        when (playerState) {
            is BlackPlayerState -> "흑"
            is WhitePlayerState -> "백"
            else -> throw IllegalArgumentException("잘 못된 값이 들어왔습니다.")
        }

    private fun changeWinner(stoneState: StoneState): String =
        when (stoneState) {
            StoneState.BLACK -> "흑"
            StoneState.WHITE -> "백"
            StoneState.NONE -> "무"
            else -> throw IllegalArgumentException("잘 못된 값이 들어왔습니다.")
        }

    companion object {
        private const val BOARD_SIZE = 15
        private const val BLACK_STONE = "○"
        private const val WHITE_STONE = "●"
        private const val START_MESSAGE = "오목 게임을 시작합니다."
    }
}
