package woowacourse.omok.view

import woowacourse.omok.domain.board.Board
import woowacourse.omok.domain.board.CellState
import woowacourse.omok.domain.board.Point
import woowacourse.omok.domain.board.result.Finished
import woowacourse.omok.domain.board.result.OnGoing
import woowacourse.omok.domain.board.result.PlaceStoneResult

class OutputView : OmokOutputView {
    override fun printStartMessage() {
        println(OMOK_STARTING_MESSAGE)
    }

    override fun printCurrentTurn(previousPoint: Pair<Point?, CellState>) {
        previousPoint.let {
            val lastPoint = PREVIOUS_POSITION_MESSAGE.format(it.first?.toAlphabet())
            println(
                CURRENT_TURN_MESSAGE.format(
                    it.second.toColorString(),
                    if (it.first != null) lastPoint else "",
                ),
            )
        }
    }

    override fun printWinColor(winnerState: CellState?) {
        println(WINNING_MESSAGE.format(winnerState.toColorString()))
    }

    override fun printBoardStatus(board: Board) {
        println("\n" + board.toUiString())
    }

    override fun printMessage(result: PlaceStoneResult) {
        when (result) {
            is OnGoing.AlreadyPlaced -> ALREADY_PLACED_ERROR_MESSAGE
            is OnGoing.RuleViolation -> VIOLATION_ERROR_MESSAGE
            is OnGoing.InvalidMove -> INVALID_POINT_ERROR_MESSAGE
            is Finished.BoardFull -> BOARD_FULL_ERROR_MESSAGE
            else -> null
        }?.let { println(ERROR_MESSAGE_PREFIX.format(it)) }
    }

    companion object {
        private const val OMOK_STARTING_MESSAGE = "[GAME START] 오목 게임을 시작합니다."
        private const val CURRENT_TURN_MESSAGE = "%s의 차례입니다 %s"
        private const val PREVIOUS_POSITION_MESSAGE = "(마지막 돌의 위치: %s)"
        private const val WINNING_MESSAGE = "[GAME FINISH] %s이 승리했습니다."
        private const val ERROR_MESSAGE_PREFIX = "[ERROR] %s"

        private const val ALREADY_PLACED_ERROR_MESSAGE = "중복되는 칸에 돌을 둘 수 없습니다."
        private const val VIOLATION_ERROR_MESSAGE = "둘 수 없는 자리입니다."
        private const val INVALID_POINT_ERROR_MESSAGE = "바둑판 크기를 벗어난 위치입니다."
        private const val BOARD_FULL_ERROR_MESSAGE = "무승부! - 바둑판에 더 이상 둘 수 있는 공간이 없습니다."
    }
}
