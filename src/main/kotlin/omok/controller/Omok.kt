package omok.controller

import omok.model.domain.gameState.BlackTurn
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
        var currentTurn: GameState = BlackTurn()
        var lastPosition: Position? = null
        var position: Position = position(currentTurn, board)
        while (currentTurn is GameState.Playing) {
            val lastTurn: GameState.Playing = currentTurn
            currentTurn = currentTurn.play(board, position)
            lastPosition = lastPosition(currentTurn, lastTurn, lastPosition, position)
            omokView.show(board)
            if (currentTurn is GameState.Finish) break
            position = position(currentTurn, board, lastPosition, lastTurn)
        }
        omokView.show(currentTurn.stone)
    }

    private fun board(): Board {
        val board = DefaultBoard()
        omokView.start(board)
        return board
    }

    private fun position(
        currentTurn: GameState,
        board: Board,
        lastPosition: Position? = null,
        lastTurn: GameState.Playing? = null,
    ): Position =
        omokView.position(
            stone = currentTurn.stone,
            boundary = board.sideLength.value,
            lastPosition = lastPosition,
            forbiddenPosition = if (currentTurn.forbidden(lastTurn)) lastPosition else null,
        )

    private fun lastPosition(
        currentTurn: GameState,
        lastTurn: GameState.Playing,
        lastPosition: Position?,
        position: Position,
    ): Position? = if (currentTurn.forbidden(lastTurn)) lastPosition else position

    private fun GameState.forbidden(lastTurn: GameState.Playing?): Boolean = this == lastTurn
}
