package controller

import model.domain.OmokGame
import model.domain.state.BlackTurn
import model.domain.state.State
import model.domain.state.Turn
import model.domain.tools.Board
import view.BoardView
import view.GuideView

class OmokController(
    private val guideView: GuideView,
) {

    private lateinit var state: State
    fun run() {
        val board = Board.from(BOARD_SIZE)
        startGame(board)
        playOmokGame()
        stopGame(board)
    }

    private fun startGame(board: Board) {
        state = BlackTurn(board)
        guideView.printStart()
        BoardView.printBoard(board)
    }

    private fun playOmokGame() {
        val omokGame = OmokGame()
        while (state is Turn) {
            playOneTurn(omokGame)
        }
    }

    private fun playOneTurn(omokGame: OmokGame) {
        state = omokGame.playNextTurn(state, guideView::requestCoordination)
        guideView.printStart()
        BoardView.printBoard(state.board)
    }

    private fun stopGame(board: Board) {
        guideView.printWinner(state.stone)
        BoardView.printBoard(board)
    }

    companion object {
        private const val BOARD_SIZE = 15
    }
}
