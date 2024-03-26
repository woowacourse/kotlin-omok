package omok.controller

import omok.model.Board
import omok.model.Player
import omok.model.Position
import omok.model.Stone
import omok.model.rule.GamePlayingRules
import omok.view.InputView
import omok.view.OutputView

class OmokController(
    private val inputView: InputView,
    private val outputView: OutputView,
    blackStoneGamePlayingRules: GamePlayingRules,
    whiteStoneGamePlayingRules: GamePlayingRules,
) {
    private val blackStonePlayer = Player(Stone.BLACK, blackStoneGamePlayingRules)
    private val whiteStonePlayer = Player(Stone.WHITE, whiteStoneGamePlayingRules)

    fun startGame() {
        val board = initializedBoard()
        val winner = gameWinner(board)
        outputView.printWinner(winner.stone)
    }

    private fun initializedBoard(): Board {
        return Board().apply { outputView.printInitialGuide(this) }
    }

    private fun gameWinner(board: Board): Player {
        var recentPlayer = blackStonePlayer
        var recentPosition: Position? = null

        while (true) {
            recentPosition = recentPosition.next(board, recentPlayer)
            outputView.printBoard(board)
            if (recentPlayer.isWin(board, recentPosition)) break
            recentPlayer = recentPlayer.next()
        }
        return recentPlayer
    }

    private fun Position?.next(
        board: Board,
        recentPlayer: Player,
    ): Position {
        return retryUntilNotException {
            val nextPosition = inputView.readStonePosition(recentPlayer.stone, this)
            board.place(nextPosition, recentPlayer)
            nextPosition
        }
    }

    private fun Player.next() = if (stone == Stone.BLACK) whiteStonePlayer else blackStonePlayer
}

fun <T> retryUntilNotException(block: () -> (T)): T {
    return try {
        block()
    } catch (e: IllegalArgumentException) {
        println(e.localizedMessage)
        retryUntilNotException(block)
    }
}
