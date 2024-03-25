package omok.controller

import omok.model.BlackStonePlayer
import omok.model.Board
import omok.model.Player
import omok.model.Players
import omok.model.WhiteStonePlayer
import omok.retryWhileNotException
import omok.view.InputView
import omok.view.OutputView

class OmokController {
    private val outputView = OutputView()
    private val inputView = InputView()
    private val board = Board()
    private val whitePlayer = WhiteStonePlayer(board)
    private val blackPlayer = BlackStonePlayer(board)
    private val players = Players(whitePlayer, blackPlayer)

    fun start() {
        outputView.showGameStartHeader()
        outputView.showBoard(blackPlayer, whitePlayer)

        players.playGame(
            putStone = { player -> putStone(player) },
            getResult = { showBoard() },
        )

        outputView.showGameResult(players.winner.color)
    }

    private fun putStone(player: Player) =
        retryWhileNotException {
            val point = inputView.getPoint(player.color, player.lastStone())
            point
        }

    private fun showBoard() {
        outputView.showBoard(blackPlayer = blackPlayer, whitePlayer = whitePlayer)
    }
}
