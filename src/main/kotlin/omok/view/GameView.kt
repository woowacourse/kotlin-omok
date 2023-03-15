package omok.view

import omok.domain.ColorState
import omok.domain.Position

class GameView {
    private val omokBoard = StringBuilder(OMOK_BOARD)

    fun printStartMessage() {
        println(START_MSG)
        println(omokBoard)
    }

    fun readPosition(colorState: ColorState, lastPosition: String?): String {
        when (colorState) {
            ColorState.Black -> println("%s의 차례입니다.".format("흑"))
            ColorState.White -> println("%s의 차례입니다.".format("백"))
        }
        lastPosition?.let { println(" (마지막 돌의 위치: %s)".format(lastPosition)) }
        print("위치를 입력하세요: ")
        return readln()
    }

    fun printBoard(colorState: ColorState, position: Position) {
        val index = ((position.horizontalAxis.value) * 3) + ((15 - position.verticalAxis) * 47)
        when (colorState) {
            ColorState.Black -> omokBoard.setCharAt(index, '●')
            ColorState.White -> omokBoard.setCharAt(index, '○')
        }
        println(omokBoard)
    }

    fun printWinMessage(colorState: ColorState) {
        when (colorState) {
            ColorState.Black -> println("%s의 승리입니다.".format("흑"))
            ColorState.White -> println("%s의 승리입니다.".format("백"))
        }
    }

    companion object {
        private const val START_MSG = "오목 게임을 시작합니다."
        private const val OMOK_BOARD =
            "15 ┌──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┐\n" +
                "14 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n" +
                "13 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n" +
                "12 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n" +
                "11 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n" +
                "10 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n" +
                " 9 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n" +
                " 8 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n" +
                " 7 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n" +
                " 6 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n" +
                " 5 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n" +
                " 4 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n" +
                " 3 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n" +
                " 2 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤\n" +
                " 1 └──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┘\n" +
                "   A  B  C  D  E  F  G  H  I  J  K  L  M  N  O"
    }
}
