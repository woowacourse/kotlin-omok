package omok.controller

import omok.model.Board
import omok.model.Position
import omok.view.InputView
import omok.view.OutputView

class OmokController(val inputView: InputView, val outputView: OutputView) {
    private val board = Board()

    fun start() {
        outputView.showGameStartMessage()

        while (true) {
            outputView.showCurrentBoard(board.status)
            val position = getInputPosition()
            val result = board.place(Position.of(position.first, position.second))
            println(result)
            result?.let {
                outputView.showGameResult(it)
                return
            }
        }
    }

    private fun getInputPosition(): Pair<Int, Char> = inputView.inputPosition(board.stones.lastOrNull())
}
