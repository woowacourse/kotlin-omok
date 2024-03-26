package omok

import omok.model.Board
import omok.model.GameState
import omok.model.Position

interface GamePlayHandler {
    fun readPosition(): Position

    fun printBoard(board: Board)

    fun printRunningInfo(gameState: GameState)
}
