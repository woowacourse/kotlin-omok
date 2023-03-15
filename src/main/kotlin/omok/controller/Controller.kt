package omok.controller

import omok.domain.Game
import omok.domain.Turn
import omok.domain.board.Board
import omok.domain.judgment.Referee
import omok.domain.player.Black
import omok.view.InputView
import omok.view.OutputView

class Controller(private val inputView: InputView, private val outputView: OutputView) {
    fun start() {
        val game = Game(Board(), Turn(Black), Referee())
        game.start(outputView::printStart, inputView::readPosition, outputView::printBoard, outputView::printWinner)
    }
}
