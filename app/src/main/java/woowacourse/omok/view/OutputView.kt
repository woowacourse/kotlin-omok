package woowacourse.omok.view

import woowacourse.omok.model.board.Board
import woowacourse.omok.model.board.Point

class OutputView : OmokOutputView {
    override fun printStartMessage() {
        println(OMOK_STARTING_MESSAGE)
    }

    override fun printCurrentTurn(previousPoint: Point?) {
        val lastPoint = previousPoint?.let { PREVIOUS_POSITION_MESSAGE.format(it.toUiString()) } ?: ""
        println(CURRENT_TURN_MESSAGE.format(previousPoint.toNextTurnColor(), lastPoint))
    }

    override fun printWinColor(previousPoint: Point) {
        println(WINNING_MESSAGE.format(previousPoint.toColorString()))
    }

    override fun printBoardStatus(board: Board) {
        println("\n" + board.toUiString())
    }

    override fun printErrorMessage(message: String) {
        println(ERROR_MESSAGE_PREFIX.format(message))
    }

    companion object {
        private const val OMOK_STARTING_MESSAGE = "[GAME START] 오목 게임을 시작합니다."
        private const val CURRENT_TURN_MESSAGE = "%s의 차례입니다 %s"
        private const val PREVIOUS_POSITION_MESSAGE = "(마지막 돌의 위치: %s)"
        private const val WINNING_MESSAGE = "[GAME FINISH] %s이 승리했습니다."
        private const val ERROR_MESSAGE_PREFIX = "[ERROR] %s"
    }
}
