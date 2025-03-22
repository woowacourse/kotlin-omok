package omok.controller

import omok.model.board.Board
import omok.model.board.Point
import omok.model.board.PointState

interface OmokGameListener {
    fun onStartGame()

    fun onRequestPosition(previousPoint: Pair<Point?, PointState>): Point

    fun onBoardUpdated(board: Board)

    fun onGameWon(winnerState: PointState?)

    fun onError(message: String)
}
