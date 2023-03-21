package view

import domain.CoordinateState
import domain.CoordinateState.BLACK
import domain.CoordinateState.EMPTY
import domain.CoordinateState.WHITE
import domain.Position
import domain.domain.Row
import domain.domain.Stones
import domain.view.constant.ViewConstant.BOARD_SIZE

object OutputView {
    private const val BLACK_TEXT = "흑"
    private const val WHITE_TEXT = "백"

    fun printStart() {
        println("오목 게임을 시작합니다.")
    }

    fun printBoard(stones: Stones) {
        stones.stones.forEachIndexed { y, row ->
            print("${BOARD_SIZE - y} ".padStart(4, ' '))
            loopRowElements(y, row)
            println()
        }
        print("    ")
        AlphabetCoordinate.values().forEach {
            print(it.name + "  ")
        }
        println()
    }

    private fun loopRowElements(y: Int, row: Row) {
        row.row.forEachIndexed { x, color ->
            printWhiteStone(color)
            printBlackStone(color)
            printEmpty(x, y, color)
        }
    }

    private fun printWhiteStone(color: CoordinateState) {
        if (color == WHITE) print(BoardParts.WHITE_STONE.value)
    }

    private fun printBlackStone(color: CoordinateState) {
        if (color == BLACK) print(BoardParts.BLACK_STONE.value)
    }

    private fun printEmpty(x: Int, y: Int, color: CoordinateState) {
        if (color == EMPTY) print(BoardParts.getPart(x, y).value)
        if (x != BOARD_SIZE - 1) {
            repeat(2) {
                print(BoardParts.GENERAL.value)
            }
        }
    }

    fun printTurn(turn: CoordinateState) {
        print("${turn.toName()}의 차례입니다.")
    }

    fun printLastPosition(position: Position?) {
        if (position == null) {
            println()
            return
        }
        println(" (마지막 돌의 위치: ${AlphabetCoordinate.convertAlphabet(position.x)}${BOARD_SIZE - position.y})")
    }

    fun printRequestPosition() {
        print("위치를 입력하세요: ")
    }

    fun printWinner(turn: CoordinateState) {
        println("${turn.toName()}의 승리입니다!!!")
    }

    private fun CoordinateState.toName(): String {
        return if (this == BLACK) BLACK_TEXT else WHITE_TEXT
    }

    fun printError() {
        println("잘못된 입력입니다. 다시 입력해주세요!")
    }
}
