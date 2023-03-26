package controller

import model.domain.OmokGame
import model.domain.state.BlackTurn
import model.domain.state.State
import model.domain.state.Turn
import model.domain.tools.Board
import view.BoardView
import view.GuideView
import view.OmokBoardViewMaker.BOARD_SIZE

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
        val dot = guideView.requestCoordination(state.stone)
        state = omokGame.playNextTurn(state, dot)
        guideView.printStart()
        BoardView.printBoard(state.board)
    }

    private fun stopGame(board: Board) {
        guideView.printWinner(state.stone)
        BoardView.printBoard(board)
    }
}
