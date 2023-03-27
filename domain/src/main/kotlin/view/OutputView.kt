package domain.view

import domain.domain.Board
import domain.domain.CoordinateState
import domain.domain.Position

object OutputView {

    fun printStart() {
        println("오목 게임을 시작합니다.")
    }

    fun printBoard(board: Board) {
        board.boardState.value.forEachIndexed { y, colors ->
            print("${Board.BOARD_SIZE - y} ".padStart(4, ' '))
            colors.forEachIndexed { x, color ->
                if (color == CoordinateState.WHITE) print(BoardParts.WHITE_STONE.value)
                if (color == CoordinateState.BLACK) print(BoardParts.BLACK_STONE.value)
                if (color == CoordinateState.EMPTY) print(BoardParts.getPart(x, y).value)
                if (x != Board.BOARD_SIZE - 1) {
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

    fun printTurn(turn: CoordinateState) {
        print("${turn.toName()}의 차례입니다.")
    }

    fun printLastPosition(position: Position?) {
        if (position == null) {
            println()
            return
        }
        println(" (마지막 돌의 위치: ${AlphabetCoordinate.convertAlphabet(position.coordinateX)}${Board.BOARD_SIZE - position.coordinateY})")
    }

    fun printRequestPosition() {
        print("위치를 입력하세요: ")
    }

    fun printWinner(turn: CoordinateState) {
        println("${turn.toName()}의 승리입니다!!!")
    }

    private fun CoordinateState.toName(): String {
        return if (this == CoordinateState.BLACK) BLACK else WHITE
    }

    fun printError() {
        println("잘못된 입력입니다. 다시 입력해주세요!")
    }

    private const val BLACK = "흑"
    private const val WHITE = "백"
}
