package omok.controller

import omok.model.Board
import omok.model.Position
import omok.model.state.GameState
import omok.view.InputView
import omok.view.OutputView

class OmokController(val inputView: InputView, val outputView: OutputView) {
    private val board: Board by lazy { Board() }

    fun start() {
        outputView.showGameStartMessage()
        playUntilFinish()
    }

    private fun playUntilFinish() {
        outputView.showCurrentBoard(board.status)
        when (val gameState = playEachTurn()) {
            is GameState.GameOver -> outputView.showGameResult(gameState.gameResult)
            is GameState.OnProgress -> playUntilFinish()
            is GameState.Error -> {
                println(gameState.message)
                playUntilFinish()
            }
        }
    }

    private fun playEachTurn(): GameState {
        val position = getInputPosition()
        return board.place(Position.of(position.first, position.second))
    }

    private fun getInputPosition(): Pair<Int, Int> = inputView.inputPosition(board.lastPlacement)
}
