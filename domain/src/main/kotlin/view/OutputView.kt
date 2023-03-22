package view

import domain.*

object OutputView {

    fun printCurrentState(omokGame: OmokGame) {
        printBoard(omokGame.board)
        printTurn(omokGame.currentColor)
        printLastPosition(omokGame.board.stones.getLastStone())
    }

    fun printStart() {
        println("오목 게임을 시작합니다.")
    }

    private fun printBoard(board: Board) {
        val customBoard = generateCustomBoard(board.stones)
        customBoard.forEachIndexed { y, colors ->
            print("${Board.getSize() - y} ".padStart(4, ' '))
            colors.forEachIndexed { x, color ->
                if (color == WHITE) print(BoardParts.WHITE_STONE.value)
                if (color == BLACK) print(BoardParts.BLACK_STONE.value)
                if (color == EMPTY) print(BoardParts.getPart(x, y).value)
                if (x != Board.getSize() - 1) {
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

    private fun printTurn(color: Color) {
        when (color) {
            Color.BLACK -> print("흑의 차례입니다.")
            Color.WHITE -> print("백의 차례입니다.")
        }
    }

    private fun printLastPosition(stone: Stone?) {
        if (stone == null) {
            println()
            return
        }
        println(" (마지막 돌의 위치: ${AlphabetCoordinate.convertAlphabet(stone.position.x)}${stone.position.y})")
    }

    fun printResult(color: Color, board: Board) {
        printBoard(board)
        when (color) {
            Color.BLACK -> print("흑의 승리입니다.")
            Color.WHITE -> print("백의 승리입니다.")
        }
    }

    private fun generateCustomBoard(stones: Stones): List<List<Int>> {
        val initBoard = List(Board.getSize()) {
            MutableList(Board.getSize()) { 0 }
        }
        stones.values.forEach {
            if (it.color== Color.BLACK) {
                initBoard[Board.getSize() - it.position.y][it.position.x] = BLACK
            } else {
                initBoard[Board.getSize() - it.position.y][it.position.x] = WHITE
            }
        }
        return initBoard
    }

    private const val EMPTY = 0
    private const val BLACK = 1
    private const val WHITE = 2
}
