package controller

import model.domain.OmokGame
import model.domain.tools.Board
import view.BoardView
import view.GuideView

class OmokController {

    fun run() {
        GuideView.printStart()

        OmokGame(Board.create()).apply {
            start(GuideView::requestCoordination, BoardView::printBoard)
            getWinner(GuideView::printWinner, BoardView::printBoard)
        }
    }
}
