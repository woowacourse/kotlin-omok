package omok.controller

import omok.model.board.Board
import omok.model.board.Point
import omok.model.board.StoneColor

interface OmokGameListener {
    fun onStartGame()

    fun onRequestPosition(previousPoint: Pair<Point?, StoneColor>): Point

    fun onBoardUpdated(board: Board)

    fun onGameWon(winnerState: StoneColor?)

    fun onError(message: String)
}
