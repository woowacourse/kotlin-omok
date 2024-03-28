package omok.controller

import omok.model.BlackStones
import omok.model.Board
import omok.model.Stones
import omok.model.OmokGame
import omok.model.WhiteStones
import omok.retryWhileNotException
import omok.view.InputView
import omok.view.OutputView

class OmokController {
    private val outputView = OutputView()
    private val inputView = InputView()
    private val board = Board()
    private val whitePlayer = WhiteStones(board)
    private val blackPlayer = BlackStones(board)
    private val omokGame = OmokGame(whitePlayer, blackPlayer)

    fun start() {
        outputView.showGameStartHeader()
        outputView.showBoard(board.stones)

        omokGame.start(
            putStone = { player -> putStone(player) },
            getResult = { showBoard() },
        )

        outputView.showGameResult(omokGame.winner.color)
    }

    private fun putStone(stones: Stones) =
        retryWhileNotException {
            val point = inputView.getPoint(stones.color, board.lastStone())
            point
        }

    private fun showBoard() {
        outputView.showBoard(board.stones)
    }
}
