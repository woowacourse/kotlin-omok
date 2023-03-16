package controller

import model.domain.OmokGame
import model.domain.tools.Board
import view.BoardView
import view.GuideView

class OmokController(
    private val boardView: BoardView,
    private val guideView: GuideView,
) {

    fun run() {
        val omokGame = OmokGame(Board.create())
        guideView.printStart()
        omokGame.gameStart(guideView::requestCoordination, boardView::printBoard)
        omokGame.getWinner(boardView::printBoard, guideView::printWinner)
    }
}
