package omok

import omok.model.Board
import omok.model.GameState
import omok.model.Position

class GameManager {
    private var gameState: GameState = GameState.Running.BlackTurn.Start(Board())

    fun play(
        onTurn: (GameState) -> Unit,
        onRead: () -> Position,
        onShow: (Board) -> Unit,
    ) {
        while (isRunning()) {
            playTurn(onTurn, onRead, onShow)
        }
        showGameResult(onTurn, onShow)
    }

    private fun isRunning() = gameState is GameState.Running

    private fun playTurn(
        onTurn: (GameState) -> Unit,
        onRead: () -> Position,
        onShow: (Board) -> Unit,
    ) {
        gameState = gameState.placeStone(onTurn, onRead, onShow)
    }

    private fun showGameResult(
        onTurn: (GameState) -> Unit,
        onShow: (Board) -> Unit,
    ) {
        onTurn(gameState)
        onShow(gameState.board)
    }
}
