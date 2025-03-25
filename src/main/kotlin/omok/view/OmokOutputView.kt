package omok.view

import omok.model.board.Board
import omok.model.board.Point
import omok.model.board.StoneColor

interface OmokOutputView {
    fun printStartMessage()

    fun printCurrentTurn(previousPoint: Pair<Point?, StoneColor>)

    fun printWinColor(winnerState: StoneColor?)

    fun printBoardStatus(board: Board)

    fun printErrorMessage(message: String)
}
