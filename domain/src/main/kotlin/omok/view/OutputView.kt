package omok.view

import omok.model.game.Board
import omok.model.stone.Coordinate
import omok.model.stone.GoStone
import omok.model.stone.GoStoneColor

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

    fun printTurn(color: GoStoneColor, coordinate: Coordinate?) {
        print("\n${color.toKorean()}의 차례입니다. ")
        if (coordinate == null) println() else println("(마지막 돌의 위치: ${coordinate.toLetters()})")
    }

    fun printBoard(board: Board) {
        board.board.reversed().forEachIndexed { index, line ->
            println(printRow(line, index))
        }
        println(emptyBoard.last())
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
        omok.model.stone.GoStoneColor.BLACK -> '●'
        omok.model.stone.GoStoneColor.WHITE -> '○'
    }

    private fun GoStoneColor.toKorean(): String = when (this) {
        GoStoneColor.BLACK -> "흑"
        GoStoneColor.WHITE -> "백"
    }

    private fun Coordinate.toLetters(): String = "${this.x.toChar() + 64}${this.y}"
}
