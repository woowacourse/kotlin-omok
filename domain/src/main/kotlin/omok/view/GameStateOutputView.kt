package omok.view

import omok.model.GameState

object GameStateOutputView : GameStateOutput {
    private const val HEADER_START_MESSAGE = "오목 게임을 시작합니다."
    private const val LAST_STONE_POSITION_MESSAGE = "(마지막 돌의 위치: %s)"
    private const val FINISH_MESSAGE = "게임이 종료되었습니다~ 🥳"
    private const val RUNNING_INFO_MESSAGE = "%s의 차례입니다."
    private const val BLACK = "흑"
    private const val WHITE = "백"

    override fun printStartHeader() {
        println(HEADER_START_MESSAGE)
    }

    override fun printRunningInfo(gameState: GameState) {
        println(switchGameState(gameState))
        gameState.board.lastCoordinate?.let {
            println(LAST_STONE_POSITION_MESSAGE.format(it))
        }
    }

    private fun switchGameState(gameState: GameState): String {
        return when (gameState) {
            is GameState.Finish -> FINISH_MESSAGE
            is GameState.Running.BlackTurn -> RUNNING_INFO_MESSAGE.format(BLACK)
            is GameState.Running.WhiteTurn -> RUNNING_INFO_MESSAGE.format(WHITE)
        }
    }
}
