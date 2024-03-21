package omok.controller

import omok.model.BlackStonePlayer
import omok.model.Board
import omok.model.Color
import omok.model.WhiteStonePlayer
import omok.model.change
import omok.view.InputView
import omok.view.OutputView

class OmokController {
    private val outputView = OutputView()
    private val inputView = InputView()
    private val board = Board()
    val whitePlayer = WhiteStonePlayer()
    val blackPlayer = BlackStonePlayer()

    fun start() {
        outputView.showGameStartHeader()
        outputView.showBoard(blackPlayer, whitePlayer)
        var turn = Color.BLACK
        while (true) {
            if (turn == Color.BLACK) {
                val stone = inputView.getStone(board, Color.BLACK, board.lastStone())
                board.add(stone)
                blackPlayer.add(stone)
                outputView.showBoard(blackPlayer = blackPlayer, whitePlayer = whitePlayer)
                if (blackPlayer.checkContinuity(stone)) break
            } else {
                val stone = inputView.getStone(board, Color.WHITE, board.lastStone())
                board.add(stone)
                whitePlayer.add(stone)
                outputView.showBoard(blackPlayer = blackPlayer, whitePlayer = whitePlayer)
                if (whitePlayer.checkContinuity(stone)) break
            }
            turn = turn.change()
        }
        outputView.showGameResult(turn)
    }
}
