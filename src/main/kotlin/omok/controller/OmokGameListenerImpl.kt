package omok.controller

import omok.model.board.Board
import omok.model.board.Point
import omok.model.board.PointState
import omok.view.OmokInputView
import omok.view.OmokOutputView

class OmokGameListenerImpl(
    private val inputView: OmokInputView,
    private val outputView: OmokOutputView,
) : OmokGameListener {
    override fun onStartGame() {
        outputView.printStartMessage()
    }

    override fun onRequestPosition(previousPoint: Pair<Point?, PointState>): Point {
        outputView.printCurrentTurn(previousPoint)
        val nextPosition = inputView.readPosition()
        return Point(nextPosition.first, nextPosition.second)
    }

    override fun onBoardUpdated(board: Board) {
        outputView.printBoardStatus(board)
    }

    override fun onGameWon(winnerState: PointState?) {
        outputView.printWinColor(winnerState)
    }

    override fun onError(message: String) {
        outputView.printErrorMessage(message)
    }
}
