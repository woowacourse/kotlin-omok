package controller

import model.domain.Board
import model.domain.OmokGame
import view.BoardView
import view.GuideView

class OmokController(
    private val boardView: BoardView,
    private val guideView: GuideView,
) {

    fun run() {
        val omokGame = OmokGame(Board.create())

        omokGame.gameStart(guideView::requestCoordination, boardView::printBoard)
    }
}
