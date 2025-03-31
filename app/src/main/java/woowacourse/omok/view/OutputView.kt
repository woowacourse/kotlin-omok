package woowacourse.omok.view

import woowacourse.omok.domain.Board
import woowacourse.omok.domain.Position
import woowacourse.omok.domain.StoneType

class OutputView {
    fun printStartMessage() {
        println(MESSAGE_START)
    }

    fun drawBoard(board: Board) {
        println(BoardRenderer.render(board))
    }

    fun printTurn(
        turn: StoneType,
        lastPosition: Position? = null,
    ) {
        val message =
            buildString {
                append("${turn.toDisplayName()}의 차례입니다.")
                lastPosition?.let { append(" (마지막 돌의 위치: ${PositionParser.decode(it)})") }
            }
        println(message)
    }

    fun showWin(winner: StoneType) {
        println("${winner.toDisplayName()}의 승리입니다.")
    }

    fun showError(message: String) {
        println("[ERROR] $message")
    }

    companion object {
        private const val MESSAGE_START = "오목 게임을 시작합니다."
    }

    private fun StoneType.toDisplayName(): String =
        when (this) {
            StoneType.BLACK -> "흑"
            StoneType.WHITE -> "백"
            else -> ""
        }
}
