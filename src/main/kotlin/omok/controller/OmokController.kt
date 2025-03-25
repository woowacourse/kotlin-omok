package omok.controller

import omok.domain.OmokBoard
import omok.domain.OmokGame
import omok.domain.state.Finished
import omok.view.InputView
import omok.view.OutputView

class OmokController(
    private val inputView: InputView,
    private val outputView: OutputView,
) {
    fun run() {
        val board = OmokBoard()
        val game = OmokGame(board)
        outputView.printStartOmok(board.boardSize)
        playOmok(game, board.boardSize)
        displayWinner(game)
    }

    private fun playOmok(
        game: OmokGame,
        boardSize: Int,
    ) {
        runCatching {
            game.play(
                onTurn = { stoneColor, lastPoint -> outputView.printTurn(stoneColor, lastPoint) },
                onPointSelected = { inputView.getPoint() },
                onBoardUpdated = { black, white ->
                    outputView.printOmokBoard(black.points, white.points, boardSize)
                },
            )
        }.getOrElse {
            println(it.message)
            playOmok(game, boardSize)
        }
    }

    private fun displayWinner(game: OmokGame) {
        if (game.state is Finished) {
            outputView.printWinner((game.state as Finished).winnerColor)
        }
    }
}
