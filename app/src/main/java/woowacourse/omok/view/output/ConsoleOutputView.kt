package woowacourse.omok.view.output

import woowacourse.omok.model.board.Board
import woowacourse.omok.model.entity.Stone
import woowacourse.omok.model.entity.StoneColor

class ConsoleOutputView : OutputView {
    override fun printStartGuide() = println("오목 게임을 시작합니다")

    override fun printTurn(board: Board) {
        val omokBoard = buildOmokBoard(board)
        println(omokBoard)
        printTurnGuide(board.previousStone()?.stoneColor)
        printPreviousPoint(board.previousStone())
    }

    private fun printTurnGuide(color: StoneColor?) {
        val colorString = getColorString(color)
        print("${colorString}의 차례입니다.  ")
    }

    private fun getColorString(color: StoneColor?): String = if (color == StoneColor.BLACK) "흑" else "백"

    private fun printPreviousPoint(nullableStone: Stone?) {
        val stone = nullableStone ?: return println("")
        val (x, y) = stone.point
        val xAlphabet = intToAlphabet(x - 1)
        println("(마지막 돌의 위치: ${xAlphabet}$y)")
    }

    private fun intToAlphabet(num: Int): Char = (num + 'A'.code).toChar()

    private fun buildOmokBoard(board: Board): String {
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
            val stone = if (it.stoneColor == StoneColor.WHITE) '○' else '●'
            val pointX = (it.point.x) * BOARD_INTERVAL
            val pointY = BOARD_LENGTH - it.point.y
            val idx = (BOARD_LENGTH * BOARD_INTERVAL + BOARD_EMPTY_INTERVAL) * pointY + pointX
            boardMap.setCharAt(idx, stone)
        }
        return boardMap.toString()
    }

    override fun printWinner(board: Board) {
        val strMap = buildOmokBoard(board)
        println(strMap)
        val colorString = getColorString(board.previousStone()?.stoneColor)
        print("${colorString}이 승리했습니다")
    }

    override fun printInAppropriatePlace(message: String) = println(message)

    companion object {
        private const val BOARD_INTERVAL = 3
        private const val BOARD_LENGTH = 15
        private const val BOARD_EMPTY_INTERVAL = 2
    }
}
