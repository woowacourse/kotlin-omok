package omok.view

import omok.model.board.Position
import omok.model.board.X
import omok.model.board.Y
import omok.model.player.BlackPlayer
import omok.model.player.Player
import omok.model.player.WhitePlayer
import omok.model.stone.Stone
import omok.model.stone.StoneState

class OmokView {
    fun printStartMessage() {
        println("오목 게임을 시작합니다.")
        printOmokBoard()
    }

//    private fun printOmokBoard() {
//        val row = List(13) { "──┼" }
//        val column = ('A'..'O').toList()
//        println("1  ┌${"──┬".repeat(13)}──┐")
//        for (i in 2..9) {
//            println("$i  ├${row.joinToString("")}──┤")
//        }
//        for (i in 10..14) {
//            println("$i ├${row.joinToString("")}──┤")
//        }
//        println("15 └${"──┴".repeat(13)}──┘")
//        println("   ${column.joinToString("  ")}")
//    }

//    fun printOmokBoard(
//        positions: Map<Position, StoneState>,
// //        stoneColors: List<StoneColor>,
//    ) {
//        val boardSize = 15
//        val column = ('A'..'O').toList()
//        val board = MutableList(boardSize) { MutableList(boardSize) { if (it + 1 == 15) "┼" else "┼──" } }
//        positions.entries.forEach { (key, value) ->
//            val stone = if (value.name == StoneColor.WHITE.name) "●" else "○"
//            if (value.name != StoneState.NONE.name) board[key.y.point - 1][key.x.point - 1] = "$stone──"
//        }
//
//        for (y in 1..boardSize) {
//            val space = if (y > 9) " " else "  "
//            print("$y$space")
//            for (x in 1..boardSize) {
//                print(board[y - 1][x - 1])
//            }
//            println()
//        }
//        println("   ${column.joinToString("  ")}")
//    }

    private fun String.toNumber(): Int {
        val alphaBets = ('A'..'O').toList()
        return alphaBets.indexOfFirst { it.toString() == this } + 1
    }

    fun inputPosition(stone: Stone): Position {
        println("${stone.color()}의 차례입니다.")
        print("위치를 입력하세요: ")
        val input = readln().trim()
        val (alphaBet, number) = input.partition { it.isLetter() }
        return Position(X(alphaBet.uppercase().toNumber()), Y(number.toInt()))
    }

    fun inputPosition(player: Player): Position {
        println("${changeName(player)}의 차례입니다.")
        print("위치를 입력하세요: ")
        val input = readln().trim()
        val (alphaBet, number) = input.partition { it.isLetter() }
        return Position(X(alphaBet.uppercase().toNumber()), Y(number.toInt()))
    }

    fun inputWhitePosition(): Position {
        println("백의 차례입니다.")
        print("위치를 입력하세요: ")
        val input = readln().trim()
        val (alphaBet, number) = input.partition { it.isLetter() }
        return Position(X(alphaBet.uppercase().toNumber()), Y(number.toInt()))
    }

    fun printOmokBoard() {
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

    fun printOmokBoard(positions: Map<Position, StoneState>) {
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
        positions: Map<Position, StoneState>,
        board: MutableList<MutableList<String>>,
    ) {
        positions.forEach { (pos, stoneState) ->
            val stone =
                when (stoneState) {
                    StoneState.WHITE -> "●"
                    StoneState.BLACK -> "○"
                    else -> return@forEach
                }
            board[pos.y.point - 1][pos.x.point - 1] = if (pos.x.point != BOARD_SIZE) "$stone──" else stone
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

    fun result(player: Player) {
        println("${changeName(player)}이 승리했습니다.")
    }

    private fun changeName(player: Player): String =
        when (player) {
            is BlackPlayer -> "흑"
            is WhitePlayer -> "백"
            else -> throw IllegalArgumentException("잘 못된 값이 들어왔습니다.")
        }

    companion object {
        private const val BOARD_SIZE = 15
    }
}
