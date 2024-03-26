package omok.view

import omok.model.GameState

object GameStateOutputView {
    private const val HEADER_START_MESSAGE = "오목 게임을 시작합니다."
    private const val LAST_STONE_POSITION_MESSAGE = "(마지막 돌의 위치: %s)"
    private const val RUNNING_INFO_MESSAGE = "%s의 차례입니다."
    private const val BLACK = "흑"
    private const val WHITE = "백"
    private const val ERROR_DUPLICATED_POSITION = "이미 돌이 놓인 자리입니다. "
    private const val ERROR_BLOCKED_POSITION = "금지된 자리입니다. "
    private const val FINISH_MESSAGE = "게임이 종료되었습니다~ 🥳"

    fun printStartHeader() {
        println(HEADER_START_MESSAGE)
    }

    fun printRunningInfo(gameState: GameState) {
        println(switchGameState(gameState))
        gameState.board.lastPosition?.let {
            println(LAST_STONE_POSITION_MESSAGE.format(it.format))
        }
    }

    fun printErrorMessage(error: Throwable) {
        println(error.message)
    }

    private fun switchGameState(gameState: GameState): String {
        return when (gameState) {
            is GameState.Finish -> FINISH_MESSAGE
            is GameState.Running.BlackTurn.Start -> RUNNING_INFO_MESSAGE.format(BLACK)
            is GameState.Running.BlackTurn.Duplicate -> ERROR_DUPLICATED_POSITION + RUNNING_INFO_MESSAGE.format(BLACK)
            is GameState.Running.BlackTurn.Block -> ERROR_BLOCKED_POSITION + RUNNING_INFO_MESSAGE.format(BLACK)
            is GameState.Running.WhiteTurn.Start -> RUNNING_INFO_MESSAGE.format(WHITE)
            is GameState.Running.WhiteTurn.Duplicate -> ERROR_DUPLICATED_POSITION + RUNNING_INFO_MESSAGE.format(WHITE)
        }
    }
}
