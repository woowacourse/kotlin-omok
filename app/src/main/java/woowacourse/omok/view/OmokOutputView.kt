package omok.view

import omok.model.board.Board
import omok.model.board.Point

interface OmokOutputView {
    fun printStartMessage()

    fun printCurrentTurn(previousPoint: Point?)

    fun printWinColor(previousPoint: Point)

    fun printBoardStatus(board: Board)

    fun printErrorMessage(message: String)
}
