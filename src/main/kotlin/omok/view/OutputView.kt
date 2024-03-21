package omok.view

import omok.model.GameState

object OutputView {
    private const val HEADER_START_MESSAGE = "오목 게임을 시작합니다."
    private const val LAST_STONE_POSITION_MESSAGE = "(마지막 돌의 위치: %s)"
    private const val RUNNING_INFO_MESSAGE = "%s의 차례입니다."
    private const val BLACK = "흑"
    private const val WHITE = "백"
    private const val ERROR_GAME_NOT_RUNNING = "게임이 진행중이지 않습니다."

    fun printStartHeader() {
        println(HEADER_START_MESSAGE)
    }

    fun printRunningInfo(gameState: GameState) {
        println(RUNNING_INFO_MESSAGE.format(switchGameState(gameState)))
        gameState.board.lastPosition?.let {
            println(LAST_STONE_POSITION_MESSAGE.format(it.format))
        }
    }

    private fun switchGameState(gameState: GameState): String {
        return when (gameState) {
            is GameState.Running.BlackTurn -> BLACK
            is GameState.Running.WhiteTurn -> WHITE
            else -> throw IllegalArgumentException(ERROR_GAME_NOT_RUNNING)
        }
    }
}
