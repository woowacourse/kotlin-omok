package omok.view

import omok.domain.Position
import omok.domain.state.Turn
import omok.domain.state.Win

class GameView {
    private val omokBoard = StringBuilder(OMOK_BOARD)

    fun printStartMessage() {
        println(START_MSG)
        println(omokBoard)
    }

    fun readPosition(state: Turn, lastPosition: String?): String {
        when (state) {
            Turn.Black -> print(TURN_MSG.format(BLACK_MSG))
            Turn.White -> print(TURN_MSG.format(WHITE_MSG))
        }
        lastPosition?.let { println(LAST_STONE_MSG.format(lastPosition)) }
        print(INPUT_LAST_POSITION_MSG)
        return readln()
    }

    fun printBoard(state: Turn, position: Position) {
        val index = ((position.horizontalAxis.value) * CALCULATE_THREE) + ((CALCULATE_FIFTEEN - position.verticalAxis) * CALCULATE_FORTY_SEVEN)
        when (state) {
            Turn.Black -> omokBoard.setCharAt(index, BLACK_STONE)
            Turn.White -> omokBoard.setCharAt(index, WHITE_STONE)
        }
        println(omokBoard)
    }

    fun printWinMessage(state: Win) {
        when (state) {
            Win.Black -> println(WIN_MSG.format(BLACK_MSG))
            Win.White -> println(WIN_MSG.format(WHITE_MSG))
        }
    }

    fun printRetry() {
        println(RETRY_MES)
    }

    fun printErrorMessage(exception: Throwable) {
        println(exception)
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

        private const val TURN_MSG = "%s의 차례입니다."
        private const val BLACK_MSG = "흑"
        private const val WHITE_MSG = "백"
        private const val LAST_STONE_MSG = "(마지막 돌의 위치: %s)"
        private const val INPUT_LAST_POSITION_MSG = "위치를 입력하세요: "
        private const val RETRY_MES = "해당 위치에 돌을 놓을 수 없습니다."
        private const val BLACK_STONE = '●'
        private const val WHITE_STONE = '◌'
        private const val WIN_MSG = "%s의 승리입니다."

        private const val CALCULATE_THREE = 3
        private const val CALCULATE_FIFTEEN = 15
        private const val CALCULATE_FORTY_SEVEN = 47
    }
}
