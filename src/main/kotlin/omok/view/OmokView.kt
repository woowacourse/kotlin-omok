package omok.view

import omok.model.board.Position
import omok.model.board.X
import omok.model.board.Y
import omok.model.stone.Stone
import omok.model.stone.StoneColor

class OmokView {
    fun printStartMessage() {
        println("오목 게임을 시작합니다.")
        printOmokBoard()
    }

    private fun printOmokBoard() {
        val row = List(13) { "──┼" }
        val column = ('A'..'O').toList()
        println("1  ┌${"──┬".repeat(13)}──┐")
        for (i in 2..9) {
            println("$i  ├${row.joinToString("")}──┤")
        }
        for (i in 10..14) {
            println("$i ├${row.joinToString("")}──┤")
        }
        println("15 └${"──┴".repeat(13)}──┘")
        println("   ${column.joinToString("  ")}")
    }

    fun printOmokBoard(
        positions: List<Pair<Int, Int>>,
        stoneColors: List<StoneColor>,
    ) {
        val boardSize = 15
        val column = ('A'..'O').toList()
        val board = MutableList(boardSize) { MutableList(boardSize) { "┼──" } }
        positions.forEachIndexed { index, (x, y) ->
            val stoneColor = stoneColors[index]
            val stone = if (stoneColor == StoneColor.WHITE) "●" else "○"
            board[y - 1][x - 1] = "$stone──"
        }

        for (y in 1..boardSize) {
            print("$y  ")
            for (x in 1..boardSize) {
                print(board[y - 1][x - 1])
            }
            println()
        }
        println("   ${column.joinToString("  ")}")
    }

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
}
