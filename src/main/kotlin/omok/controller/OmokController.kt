package omok.controller

import omok.model.Board
import omok.model.Stones
import omok.view.InputView.inputStoneCoordinate
import omok.view.OutputView
import omok.view.OutputView.printForbiddenStone

class OmokController {
    fun run() {
        OutputView.printStart()
        val board = Board(Stones())

        playGame(board)
        displayWinner(board)
    }

    private fun playGame(board: Board) {
        var turn = 0
        while (board.isPlaying()) {
            OutputView.printTurnName(board.getCurrentTurn(turn))
            OutputView.printLastStone(board.stones.getLastStoneCoordinate())
            playTurnUntilSuccess(turn, board)
            OutputView.printBoard(board.stones)
            turn++
        }
    }

    private fun playTurnUntilSuccess(
        turn: Int,
        board: Board,
    ) {
        while (true) {
            val result = playTurn(turn, board)
            if (result.isSuccess) {
                break
            } else {
                result.onFailure { e -> OutputView.printErrorMessage(e.message!!) }
            }
        }
    }

    private fun playTurn(
        turn: Int,
        board: Board,
    ) = runCatching {
        board.takeTurn(
            turn,
            getCoordinate = ::inputStoneCoordinate,
            returnCurrentState = ::printForbiddenStone,
        )
    }

    private fun displayWinner(board: Board) {
        runCatching {
            OutputView.printWinner(board.getWinner())
        }.onFailure { error ->
            OutputView.printErrorMessage(error.message!!)
        }
    }
}
