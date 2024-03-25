package omok.controller

import omok.model.BlackStonePlayer
import omok.model.Board
import omok.model.Color
import omok.model.Player
import omok.model.WhiteStonePlayer
import omok.model.change
import omok.retryWhileNotException
import omok.view.InputView
import omok.view.OutputView

class OmokController {
    private val outputView = OutputView()
    private val inputView = InputView()
    private val board = Board()
    private val whitePlayer = WhiteStonePlayer(board)
    private val blackPlayer = BlackStonePlayer(board)

    fun start() {
        outputView.showGameStartHeader()
        outputView.showBoard(blackPlayer, whitePlayer)

        var turn = Color.BLACK
        while (true) {
            if (turn == Color.BLACK) {
                putStone(blackPlayer, turn)
                if (blackPlayer.checkContinuity()) break
            } else {
                putStone(whitePlayer, turn)
                if (whitePlayer.checkContinuity()) break
            }
            turn = turn.change()
        }
        outputView.showGameResult(turn)
    }

    private fun putStone(
        player: Player,
        turn: Color,
    ) =
        retryWhileNotException {
            val point = inputView.getPoint(turn, player.lastStone())
            player.add(point)
            showBoard()
        }

    private fun showBoard() {
        outputView.showBoard(blackPlayer = blackPlayer, whitePlayer = whitePlayer)
    }
}
