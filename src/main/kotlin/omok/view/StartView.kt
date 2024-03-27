package omok.view

import omok.model.board.Board

class StartView {
    fun print(
        board: Board,
        boardView: BoardView,
    ) {
        println(INITIAL_GUIDE_MESSAGE)
        boardView.print(board)
    }

    companion object {
        private const val INITIAL_GUIDE_MESSAGE = "오목 게임을 시작합니다."
    }
}
