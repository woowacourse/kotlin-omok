package omok.view.output

import omok.model.Board
import omok.model.entity.Point
import omok.model.entity.StoneColor

class ConsoleOutputView : OutputView {
    override fun printStartGuide() {
        println("오목 게임을 시작합니다")
    }

    override fun printOneTurn(board: Board, color: StoneColor) {
        val strMap = buildOmokBoard(board)
        printTurn(color)
        printPreviousPoint(board.previousPoint())
        println(strMap)
    }

    private fun printTurn(color: StoneColor) {
        val colorString = getColorString(color)
        print("${colorString}의 차례입니다.  ")
    }

    private fun getColorString(color: StoneColor): String = if (color == StoneColor.BLACK) "흑" else "백"

    fun printPreviousPoint(point: Point?) {
        val (x, y) = point ?: return println("")
        val xAlphabet = intToAlphabet(x)
        println("(마지막 돌의 위치: ${xAlphabet}$y)")
    }

    fun intToAlphabet(num: Int): Char = (num + 'A'.code).toChar()

    private fun buildOmokBoard(board: Board): String {
        val strMap = """
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
        board.map.forEach {
            val stoneChar = if (it.value == StoneColor.WHITE) '○' else '●'
            val x = (it.key.x) * 3
            val y = 15 - it.key.y
            val idx = 47 * y + x
            sb.setCharAt(idx, stoneChar)
        }
        return sb.toString()
    }
}
