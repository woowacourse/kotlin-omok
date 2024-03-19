package omok.view

import omok.model.GameState
import omok.model.StoneType

object OutputView {
    private const val HEADER_START_MESSAGE = "오목 게임을 시작합니다."

    fun printStartHeader() {
        println(HEADER_START_MESSAGE)
    }

    fun printTurnInfo(gameState: GameState) {
        println(switchStoneName(gameState.currentStone) + "의 차례입니다.")
        gameState.lastPosition?.let {
            println("(마지막 돌의 위치: ${it.format})")
        }
    }

    fun switchStoneName(stoneType: StoneType): String? {
        return when (stoneType) {
            StoneType.BLACK_STONE -> "흑"
            StoneType.WHITE_STONE -> "백"
            else -> null
        }
    }
}
