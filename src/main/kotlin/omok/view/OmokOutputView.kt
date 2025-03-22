package omok.view

import omok.model.board.Board
import omok.model.board.Point
import omok.model.board.PointState

interface OmokOutputView {
    fun printStartMessage()

    fun printCurrentTurn(previousPoint: Pair<Point?, PointState>)

    fun printWinColor(winnerState: PointState?)

    fun printBoardStatus(board: Board)

    fun printErrorMessage(message: String)
}
