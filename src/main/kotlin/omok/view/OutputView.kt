package omok.view

import omok.domain.board.OmokBoard
import omok.domain.place.Place
import omok.view.ext.toLabel

object OutputView {
    fun printErrorMessage(msg: String?) {
        println(msg)
    }

    fun printStartMessage() {
        println(MESSAGE_START_GAME)
    }

    fun printPrintWinner(place: Place) {
        println(MESSAGE_WINNER.format(place.toLabel()))
    }

    fun printBoard(board: OmokBoard) {
        print(board.view())
    }

    private const val MESSAGE_START_GAME = "오목 게임을 시작합니다."
    private const val MESSAGE_WINNER = "%s이 승리하였습니다."
}
