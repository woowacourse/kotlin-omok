package omok.controller

import omok.model.BlackStones
import omok.model.Board
import omok.model.Stones
import omok.model.Players
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
    private val players = Players(whitePlayer, blackPlayer)

    fun start() {
        outputView.showGameStartHeader()
        outputView.showBoard(board.stones)

        players.playGame(
            putStone = { player -> putStone(player) },
            getResult = { showBoard() },
        )

        outputView.showGameResult(players.winner.color)
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
