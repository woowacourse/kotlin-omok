package omok.controller

import omok.model.Board
import omok.model.Position
import omok.model.Stone
import omok.view.InputView
import omok.view.OutputView

class OmokController(
    private val inputView: InputView,
    private val outputView: OutputView,
) {
    fun startGame() {
        val board = initializedBoard()
        val winner = gameWinner(board)
        outputView.printWinner(winner)
    }

    private fun initializedBoard(): Board {
        return Board().apply { outputView.printInitialGuide(this) }
    }

    private fun gameWinner(board: Board): Stone {
        var recentStone = Stone.BLACK
        var recentPosition: Position? = null

        while (true) {
            recentPosition = recentPosition.next(board, recentStone)
            outputView.printBoard(board)
            if (board.isWin(recentPosition)) break
            recentStone = recentStone.next()
        }
        return recentStone
    }

    private fun Position?.next(board: Board, recentStone: Stone): Position {
        return retryUntilNotException {
            val nextPosition = inputView.readStonePosition(recentStone, this)
            board.place(nextPosition, recentStone)
            nextPosition
        }
    }
}

fun <T> retryUntilNotException(block: () -> (T)): T {
    return try {
        block()
    } catch (e: IllegalArgumentException) {
        println(e.localizedMessage)
        retryUntilNotException(block)
    }
}
