package domain.controller

import domain.domain.Board
import domain.domain.state.InProgress
import domain.view.OmokView

class Controller(
    private val board: Board,
    private val omokView: OmokView,
) {
    fun run() {
        board.registerObserver(omokView)
        omokView.printStart(board.state)
        while (board.state is InProgress) {
            board.next(omokView.getInputPosition())
        }
    }
}
