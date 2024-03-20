package omok.view.output

import omok.model.Board
import omok.model.entity.Stone
import omok.model.entity.StoneColor

class ConsoleOutputView : OutputView {
    override fun printStartGuide() {
        println("오목 게임을 시작합니다")
    }

    override fun printOneTurn(
        board: Board,
        color: StoneColor,
    ) {
        val strMap = buildOmokBoard(board)
        println(strMap)
        printTurn(color)
        printPreviousPoint(board.previousStone())
    }

    private fun printTurn(color: StoneColor) {
        val colorString = getColorString(color)
        print("${colorString}의 차례입니다.  ")
    }

    private fun getColorString(color: StoneColor): String = if (color == StoneColor.BLACK) "흑" else "백"

    private fun printPreviousPoint(nullableStone: Stone?) {
        val stone = nullableStone ?: return println("")
        val (x, y) = stone.point
        val xAlphabet = intToAlphabet(x - 1)
        println("(마지막 돌의 위치: ${xAlphabet}$y)")
    }

    private fun intToAlphabet(num: Int): Char = (num + 'A'.code).toChar()

    private fun buildOmokBoard(board: Board): String {
        println()
        val strMap =
            """
            15 ┌──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┐
            14 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
            13 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
            12 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
            11 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
            10 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
            9  ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
            8  ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
            7  ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
            6  ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
            5  ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
            4  ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
            3  ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
            2  ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
            1  └──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┘
               A  B  C  D  E  F  G  H  I  J  K  L  M  N  O
            """.trimIndent()
        val sb = StringBuilder(strMap)
        board.stones.forEach {
            val stoneChar = if (it.stoneColor == StoneColor.WHITE) '○' else '●'
            val x = (it.point.x) * 3
            val y = 15 - it.point.y
            val idx = 47 * y + x
            sb.setCharAt(idx, stoneChar)
        }
        return sb.toString()
    }
}
