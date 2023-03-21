package view

import domain.domain.Board2
import domain.domain.Color
import domain.domain.Position2
import domain.domain.Stones

object OutputView {

    fun printCurrentState(board: Board2) {
        printBoard(board)
        printTurn(board.getCurrentTurn())
        printLastPosition(board.getLastPosition())
    }

    fun printStart() {
        println("오목 게임을 시작합니다.")
    }

    fun printBoard(board: Board2) {
        val customBoard = generateCustomBoard(board.stones)
        customBoard.forEachIndexed { y, colors ->
            print("${Board2.getSize() - y} ".padStart(4, ' '))
            colors.forEachIndexed { x, color ->
                if (color == WHITE) print(BoardParts.WHITE_STONE.value)
                if (color == BLACK) print(BoardParts.BLACK_STONE.value)
                if (color == EMPTY) print(BoardParts.getPart(x, y).value)
                if (x != Board2.getSize() - 1) {
                    repeat(2) {
                        print(BoardParts.GENERAL.value)
                    }
                }
            }
            println()
        }
        print("    ")
        AlphabetCoordinate.values().forEach {
            print(it.name + "  ")
        }
        println()
    }

    fun printTurn(color: Color) {
        when (color) {
            Color.BLACK -> print("흑의 차례입니다.")
            Color.WHITE -> print("백의 차례입니다.")
        }
    }

    fun printLastPosition(position: Position2?) {
        if (position == null) {
            println()
            return
        }
        println(" (마지막 돌의 위치: ${AlphabetCoordinate.convertAlphabet(position.x)}${position.y})")
    }

    fun printResult(color: Color, board: Board2) {
        printBoard(board)
        when (color) {
            Color.BLACK -> print("흑의 승리입니다.")
            Color.WHITE -> print("백의 승리입니다.")
        }
    }

    private fun generateCustomBoard(stones: Stones): List<List<Int>> {
        val initBoard = List(Board2.getSize()) {
            MutableList(Board2.getSize()) { 0 }
        }
        stones.values.forEach {
            if (it.isBlack()) {
                initBoard[Board2.getSize() - it.position.y][it.position.x] = BLACK
            } else {
                initBoard[Board2.getSize() - it.position.y][it.position.x] = WHITE
            }
        }
        return initBoard
    }

    private const val EMPTY = 0
    private const val BLACK = 1
    private const val WHITE = 2
}
