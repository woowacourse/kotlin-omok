package omok.view

import omok.domain.Point
import omok.domain.StoneColor

class OutputView {
    fun printStartOmok() {
        println(MESSAGE_START_OMOK)
        println(DEFAULT_OMOK_BOARD)
    }

    fun printTurn(
        color: StoneColor,
        lastPoint: Point?,
    ) {
        print(MESSAGE_TURN.format(color.toKorean()))
        if (lastPoint != null) {
            println(MESSAGE_LAST_POINT.format(lastPoint.toText()))
        }
    }

    fun printOmokBoard(
        blackPoints: Set<Point>,
        whitePoints: Set<Point>,
    ) {
        val board = StringBuilder(DEFAULT_OMOK_BOARD)
        blackPoints.forEach { board.setCharAt(calculatePosition(it), BLACK_STONE) }
        whitePoints.forEach { board.setCharAt(calculatePosition(it), WHITE_STONE) }
        println(board)
    }

    fun printWinner(color: StoneColor?) {
        if (color != null) {
            println(MESSAGE_WINNER.format(color.toKorean()))
        } else {
            println(MESSAGE_DRAW)
        }
    }

    private fun calculatePosition(point: Point): Int = (point.x + 1) * 3 + 47 * (14 - point.y)

    companion object {
        private const val MESSAGE_START_OMOK = "오목 게임을 시작합니다."
        private const val MESSAGE_TURN = "\n%s의 차례입니다."
        private const val MESSAGE_LAST_POINT = " (마지막 돌의 위치: %s)"
        private const val MESSAGE_WINNER = "%s이 승리했습니다."
        private const val MESSAGE_DRAW = "더 이상 돌을 놓을 곳이 없습니다. 게임을 종료합니다."

        private const val BLACK_STONE = '●'
        private const val WHITE_STONE = '○'
        private val DEFAULT_OMOK_BOARD =
            """
            15 ┌──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┐
            14 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
            13 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
            12 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
            11 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
            10 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
             9 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
             8 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
             7 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
             6 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
             5 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
             4 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
             3 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
             2 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
             1 └──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┘
               A  B  C  D  E  F  G  H  I  J  K  L  M  N  O
            """.trimIndent()

        private fun StoneColor.toKorean(): String =
            when (this) {
                StoneColor.BLACK -> "흑"
                StoneColor.WHITE -> "백"
            }

        private fun Point.toText(): String = ('A' + x).toString() + (y + 1)
    }
}
