package omok.controller

import omok.model.board.Board
import omok.model.board.Point
import omok.model.board.StoneColor
import omok.view.OmokInputView
import omok.view.OmokOutputView

class OmokGameHandlerImpl(
    private val inputView: OmokInputView,
    private val outputView: OmokOutputView,
) : OmokGameHandler {
    override fun onStartGame() {
        outputView.printStartMessage()
    }

    override fun onRequestPosition(previousPoint: Pair<Point?, StoneColor>): Point {
        outputView.printCurrentTurn(previousPoint)
        val nextPosition = inputView.readPosition()
        return Point(nextPosition.first, nextPosition.second)
    }

    override fun onBoardUpdated(board: Board) {
        outputView.printBoardStatus(board)
    }

    override fun onGameWon(winnerState: StoneColor?) {
        outputView.printWinColor(winnerState)
    }

    override fun onError(message: String) {
        outputView.printErrorMessage(message)
    }
}
