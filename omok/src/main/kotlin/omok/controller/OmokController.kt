package omok.controller

import omok.model.BlackStonePlayer
import omok.model.OmokGame
import omok.model.Player
import omok.model.Stones
import omok.model.WhiteStonePlayer
import omok.retryWhileNotException
import omok.view.InputView
import omok.view.OutputView

class OmokController {
    private val outputView = OutputView()
    private val inputView = InputView()
    private val stones = Stones()
    private val whiteStonePlayer = WhiteStonePlayer(stones)
    private val blackStonePlayer = BlackStonePlayer(stones)
    private val omokGame = OmokGame(whiteStonePlayer, blackStonePlayer)

    fun start() {
        outputView.showGameStartHeader()
        outputView.showBoard(stones.stones)

        omokGame.start(
            putStone = { player -> putStone(player) },
            getResult = { showBoard() },
        )

        outputView.showGameResult(omokGame.winner.color)
    }

    private fun putStone(player: Player) =
        retryWhileNotException {
            val point = inputView.getPoint(player.color, stones.lastStone())
            point
        }

    private fun showBoard() {
        outputView.showBoard(stones.stones)
    }
}
