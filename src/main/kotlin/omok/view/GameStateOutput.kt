package omok.view

import omok.model.GameState

interface GameStateOutput {
    fun printStartHeader()

    fun printRunningInfo(gameState: GameState)
}
