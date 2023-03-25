package controller

import model.domain.OmokGame
import model.domain.state.Omok
import model.domain.tools.Board
import view.BoardView
import view.GuideView

class OmokController {

    fun run() {
        GuideView.printStart()

        OmokGame(Board.create()).apply {
            while (state !is Omok) {
                start(BoardView::printBoard, GuideView::printRequestCoordination)
                play(GuideView::requestCoordination)
            }
            getWinner(GuideView::printWinner, BoardView::printBoard)
        }
    }
}
