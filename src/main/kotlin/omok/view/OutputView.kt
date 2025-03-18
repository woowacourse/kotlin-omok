package omok.view

import omok.domain.Point
import omok.domain.StoneColor

class OutputView {
    fun printStartOmok() {
        println(MESSAGE_START_OMOK)
        println(DEFAULT_OMOK_BOARD)
    }

    fun printFirstTurn() {
        println(MESSAGE_TURN.format(StoneColor.BLACK.toKorean()))
    }

    fun printTurn(
        color: StoneColor,
        lastPoint: Point,
    ) {
        print(MESSAGE_TURN.format(color.toKorean()))
        println(MESSAGE_LAST_POINT.format(lastPoint.toText()))
    }

    fun printWinner(color: StoneColor) {
        println(MESSAGE_WINNER.format(color.toKorean()))
    }

    companion object {
        private const val MESSAGE_START_OMOK = "오목 게임을 시작합니다."
        private const val MESSAGE_TURN = "%s의 차례입니다."
        private const val MESSAGE_LAST_POINT = " (마지막 돌의 위치: %s)"
        private const val MESSAGE_WINNER = "%s이 승리했습니다."

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
