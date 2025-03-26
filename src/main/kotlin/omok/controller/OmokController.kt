package omok.controller

import omok.domain.OmokBoard
import omok.domain.OmokGame
import omok.domain.rule.OmokRule
import omok.view.InputView
import omok.view.OutputView

class OmokController(
    private val inputView: InputView,
    private val outputView: OutputView,
) {
    fun run() {
        val rule = OmokRule()
        val board = OmokBoard(rule = rule)
        val game = OmokGame(board)
        outputView.printStartOmok(board.size)
        playOmok(game)
        outputView.printWinner(game.winner())
    }

    private fun playOmok(game: OmokGame) {
        runCatching {
            game.play(
                onTurn = { stoneColor, lastPoint -> outputView.printTurn(stoneColor, lastPoint) },
                onPointSelected = { inputView.getPoint() },
                onBoardUpdated = { board ->
                    outputView.printOmokBoard(board.stones, board.size)
                },
            )
        }.getOrElse {
            println(it.message)
            playOmok(game)
        }
    }
}
