package controller

import model.domain.OmokGame
import model.domain.tools.Board
import view.BoardView
import view.GuideView

class OmokController() {

    fun run() {
        val omokGame = OmokGame(Board.create())
        GuideView.printStart()
        omokGame.gameStart(GuideView::requestCoordination, BoardView::printBoard)
        omokGame.getWinner(BoardView::printBoard, GuideView::printWinner)
    }
}
