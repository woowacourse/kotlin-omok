package omok.controller

import omok.model.domain.gameState.GameState
import omok.model.entity.board.Board
import omok.model.entity.board.DefaultBoard
import omok.model.entity.position.Position
import omok.view.OmokView

class Omok(
    private val omokView: OmokView,
) {
    fun play() {
        val board = board()
        var gameState = GameState(board)
        var lastPosition: Position? = null
        var position: Position = position(gameState)
        while (gameState.playing) {
            val lastGameState: GameState = gameState
            gameState = gameState.play(position)
            lastPosition = lastPosition(gameState, lastGameState, lastPosition, position)
            omokView.show(board)
            if (gameState.playing) position = position(gameState, lastPosition, lastGameState)
        }
        omokView.show(gameState.stone)
    }

    private fun board(): Board {
        val board = DefaultBoard()
        omokView.start(board)
        return board
    }

    private fun position(
        gameState: GameState,
        lastPosition: Position? = null,
        lastGameState: GameState? = null,
    ): Position =
        omokView.position(
            stone = gameState.stone,
            boundary = gameState.board.sideLength.value,
            lastPosition = lastPosition,
            forbiddenPosition = if (gameState.forbidden(lastGameState)) lastPosition else null,
        )

    private fun lastPosition(
        gameState: GameState,
        lastGameState: GameState,
        lastPosition: Position?,
        position: Position,
    ): Position? = if (gameState.forbidden(lastGameState)) lastPosition else position

    private fun GameState.forbidden(lastTurn: GameState?): Boolean = this == lastTurn
}
