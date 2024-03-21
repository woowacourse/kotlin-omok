package omok.controller

import omok.model.BlackStonePlayer
import omok.model.Board
import omok.model.Color
import omok.model.Player
import omok.model.Stone
import omok.model.WhiteStonePlayer
import omok.model.change
import omok.view.InputView
import omok.view.OutputView

class OmokController {
    private val outputView = OutputView()
    private val inputView = InputView()
    private val board = Board()
    private val whitePlayer = WhiteStonePlayer()
    private val blackPlayer = BlackStonePlayer()

    fun start() {
        outputView.showGameStartHeader()
        outputView.showBoard(blackPlayer, whitePlayer)

        var turn = Color.BLACK
        while (true) {
            if (turn == Color.BLACK) {
                val stone = putStone(blackPlayer, turn)
                if (blackPlayer.checkContinuity(stone)) break
            } else {
                val stone = putStone(whitePlayer, turn)
                if (whitePlayer.checkContinuity(stone)) break
            }
            turn = turn.change()
        }
        outputView.showGameResult(turn)
    }

    private fun putStone(
        player: Player,
        turn: Color,
    ): Stone {
        val stone = inputView.getStone(board, turn, board.lastStone())
        board.add(stone)
        player.add(stone)
        showBoard()
        return stone
    }

    private fun showBoard() {
        outputView.showBoard(blackPlayer = blackPlayer, whitePlayer = whitePlayer)
    }
}
