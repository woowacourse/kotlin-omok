package omok.view

import omok.model.GameState

object OutputView {
    private const val HEADER_START_MESSAGE = "오목 게임을 시작합니다."

    fun printStartHeader() {
        println(HEADER_START_MESSAGE)
    }

    fun printRunningInfo(gameState: GameState) {
        println(switchGameState(gameState) + "의 차례입니다.")
        gameState.board.lastPosition?.let {
            println("(마지막 돌의 위치: ${it.format})")
        }
    }

    private fun switchGameState(gameState: GameState): String {
        return when (gameState) {
            is GameState.Running.BlackTurn -> "흑"
            is GameState.Running.WhiteTurn -> "백"
            else -> throw IllegalArgumentException("게임이 진행중이지 않습니다.")
        }
    }
}
