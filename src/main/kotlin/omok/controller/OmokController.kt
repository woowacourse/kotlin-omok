package omok.controller

import omok.domain.OmokBoard
import omok.domain.OmokGame
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
        playOmok(game)
        outputView.printWinner(game.winner())
    }

    private fun playOmok(game: OmokGame) {
        runCatching {
            game.play(
                onTurn = { stoneColor, lastPoint -> outputView.printTurn(stoneColor, lastPoint) },
                onPointSelected = { inputView.getPoint() },
                onBoardUpdated = { board ->
                    outputView.printOmokBoard(
                        board.blackStones.points,
                        board.whiteStones.points,
                        board.boardSize,
                    )
                },
            )
        }.getOrElse {
            println(it.message)
            playOmok(game)
        }
    }
}
