package omok.view

import omok.model.game.Board
import omok.model.state.State
import omok.model.stone.Coordinate
import omok.model.stone.GoStone
import omok.model.stone.GoStoneColor
import omok.view.OutputView.toKorean

object OutputView {
    private const val LEFT_PADDING: Int = 4
    private const val GAP_BETWEEN_STONES: Int = 3
    private val emptyBoard = listOf(
        " 15 ┌──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┐",
        " 14 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤",
        " 13 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤",
        " 12 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤",
        " 11 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤",
        " 10 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤",
        "  9 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤",
        "  8 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤",
        "  7 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤",
        "  6 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤",
        "  5 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤",
        "  4 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤",
        "  3 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤",
        "  2 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤",
        "  1 └──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┘",
        "    A  B  C  D  E  F  G  H  I  J  K  L  M  N  O",
    )

    fun printInitGame() {
        println("오목 게임을 시작합니다.\n")
        println(emptyBoard.joinToString("\n"))
    }

    fun printTurn(color: GoStoneColor, stone: GoStone?) {
        print("\n${color.toKorean()}의 차례입니다. ")
        if (stone == null) println() else println("(마지막 돌의 위치: ${stone.coordinate.toMark()})")
    }

    fun printBoard(board: Board) {
        for (line in board.board.withIndex().reversed()) {
            println(printRow(line.value, line.value.size - line.index - 1))
        }
        println(emptyBoard.last())
    }

/*    fun printResult(result: GoStoneColor) {
        println("${result.toKorean()}이 승리했습니다!\n")
    }*/

    fun printState(state: State, color: GoStoneColor) {
        when (state) {
            State.Win -> println("${color.toKorean()}이 승리했습니다!\n")
            State.DoubleThree -> println("해당 위치는 돌을 놓을 수 없습니다. (3-3 금수)")
            State.DoubleFour -> println("해당 위치는 돌을 놓을 수 없습니다. (4-4 금수)")
            State.Stay -> println()
        }
    }

    private fun printRow(stones: List<GoStone?>, rowIndex: Int): String {
        val stringBuilder = StringBuilder(emptyBoard[rowIndex])

        stones.forEach { stone ->
            val index = stones.indexOf(stone)
            stone?.let { stringBuilder.setCharAt(LEFT_PADDING + index * GAP_BETWEEN_STONES, it.toMark()) }
        }

        return stringBuilder.toString()
    }

    private fun GoStone.toMark() = when (this.color) {
        GoStoneColor.BLACK -> '●'
        GoStoneColor.WHITE -> '◌'
    }

    private fun Coordinate.toMark(): String = "${'A' + this.x}${this.y + 1}"

    private fun GoStoneColor.toKorean(): String = when (this) {
        GoStoneColor.BLACK -> "흑"
        GoStoneColor.WHITE -> "백"
    }
}
