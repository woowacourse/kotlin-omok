package omok.controller

import omok.domain.OmokGame
import omok.domain.Point
import omok.domain.state.Finished
import omok.view.InputView
import omok.view.OutputView

class OmokController(
    private val inputView: InputView,
    private val outputView: OutputView,
) {
    fun run() {
        val game = OmokGame()
        outputView.printStartOmok(game.boardSize)
        playOmok(game)
        displayWinner(game)
    }

    private fun playOmok(
        game: OmokGame,
        lastPoint: Point? = null,
    ) {
        var currentPoint = lastPoint
        runCatching {
            game.play(
                onTurn = { stoneColor -> outputView.printTurn(stoneColor, currentPoint) },
                onPointSelected = {
                    val point = inputView.getPoint()
                    currentPoint = point
                    point
                },
                onBoardUpdated = { black, white ->
                    outputView.printOmokBoard(black, white, game.boardSize)
                },
            )
        }.getOrElse {
            println(it.message)
            playOmok(game, currentPoint)
        }
    }

    private fun displayWinner(game: OmokGame) {
        if (game.state is Finished) {
            outputView.printWinner((game.state as Finished).winnerColor)
        }
    }
}
