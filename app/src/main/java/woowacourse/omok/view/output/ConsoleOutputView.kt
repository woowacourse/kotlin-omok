package woowacourse.omok.view.output

import woowacourse.omok.model.board.Board

class ConsoleOutputView : OutputView {
    override fun printTurn(
        stoneColor: String?,
        point: Pair<Int, Int>?,
    ) {
        printTurnGuide(stoneColor)
        printPreviousPoint(point)
    }

    private fun printTurnGuide(stoneColor: String?) = print("${getColorString(stoneColor)}의 차례입니다.")

    override fun printWinner(stoneColor: String?) = print("${getColorString(stoneColor)}이 승리했습니다")

    override fun printAlert(message: String) = println(message)

    fun printOmokBoard(board: Board) {
        println()
        val boardMap =
            StringBuilder(
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
                """.trimIndent(),
            )
        board.stones.forEach {
            val stone = if (it.stoneColor.name == "WHITE") '○' else '●'
            val pointX = (it.point.x) * BOARD_INTERVAL
            val pointY = BOARD_LENGTH - it.point.y
            val idx = (BOARD_LENGTH * BOARD_INTERVAL + BOARD_EMPTY_INTERVAL) * pointY + pointX
            boardMap.setCharAt(idx, stone)
        }
        println(boardMap.toString())
    }

    private fun getColorString(color: String?): String = if (color == "BLACK") "흑" else "백"

    private fun printPreviousPoint(point: Pair<Int, Int>?) {
        val (x, y) = point ?: return println("")
        val xAlphabet = intToAlphabet(x - 1)
        println("(마지막 돌의 위치: ${xAlphabet}$y)")
    }

    private fun intToAlphabet(num: Int): Char = (num + 'A'.code).toChar()

    companion object {
        private const val BOARD_INTERVAL = 3
        private const val BOARD_LENGTH = 15
        private const val BOARD_EMPTY_INTERVAL = 2
    }
}
