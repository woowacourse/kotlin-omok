package omok.controller

import omok.model.BlackStonePlayer
import omok.model.WhiteStonePlayer
import omok.view.InputView
import omok.view.OutputView

class OmokController {
    private val outputView = OutputView()
    private val inputView = InputView()
    val whitePlayer = WhiteStonePlayer()
    val blackPlayer = BlackStonePlayer()

    fun start() {
        outputView.printBoard(blackPlayer, whitePlayer)
        var turn = false
        while (true) {
            if (!turn) {
                val stone = inputView.getStone(turn)
                blackPlayer.add(stone)

                outputView.printBoard(blackPlayer = blackPlayer, whitePlayer = whitePlayer)
                if (blackPlayer.checkContinuity(stone)) break
            } else {
                val stone = inputView.getStone(turn)
                whitePlayer.add(stone)
                outputView.printBoard(blackPlayer = blackPlayer, whitePlayer = whitePlayer)
                if (whitePlayer.checkContinuity(stone)) break
            }
            turn = !turn
        }
    }
}
