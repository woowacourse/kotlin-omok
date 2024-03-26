package omok

import omok.model.Board
import omok.model.GameState
import omok.model.Position
import omok.view.BoardOutputView
import omok.view.GameStateOutputView
import omok.view.PositionInputView

interface GamePlayHandler {
    fun readPosition(): Position

    fun printBoard(board: Board)

    fun printRunningInfo(gameState: GameState)
}
