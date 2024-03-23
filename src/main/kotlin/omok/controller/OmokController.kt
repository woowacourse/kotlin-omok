package omok.controller

import omok.model.Board
import omok.model.GameResult
import omok.model.Position
import omok.view.InputView
import omok.view.OutputView

class OmokController(val inputView: InputView, val outputView: OutputView) {
    private val board: Board by lazy { Board() }

    fun start() {
        outputView.showGameStartMessage()
        playUntilFinish()
    }

    private fun playUntilFinish() {
        runCatching {
            outputView.showCurrentBoard(board.status)
            val result = playEachTurn()
            if (result == GameResult.PROCEEDING) {
                return playUntilFinish()
            } else {
                outputView.showGameResult(result)
            }
        }.onFailure {
            println(it.message)
            return playUntilFinish()
        }
    }

    private fun playEachTurn(): GameResult {
        val position = getInputPosition()
        return board.place(Position.of(position.first, position.second))
    }

    private fun getInputPosition(): Pair<Int, Char> = inputView.inputPosition(board.stones.lastOrNull())
}
