package woowacourse.omok.domain.omok.controller

import woowacourse.omok.domain.omok.model.Board
import woowacourse.omok.domain.omok.model.GameResult
import woowacourse.omok.domain.omok.model.Position
import woowacourse.omok.domain.omok.view.InputView
import woowacourse.omok.domain.omok.view.OutputView

class OmokController(val inputView: InputView, val outputView: OutputView) {
    private val board: Board by lazy { Board() }

    fun start() {
        outputView.showGameStartMessage()
        playUntilFinish()
    }

    private tailrec fun playUntilFinish() {
        runCatching {
            outputView.showCurrentBoard(board.status)
            val inputPosition = getInputPosition()
            val position = Position.of(inputPosition.first, inputPosition.second)
            placeEachTurn(position)
        }.onFailure {
            println(it.message)
            return playUntilFinish()
        }
    }

    private fun placeEachTurn(position: Position) {
        board.place(position)
        val result = board.getGameResult(position)
        if (result == GameResult.PROCEEDING) {
            playUntilFinish()
        } else {
            outputView.showGameResult(result)
        }
    }

    private fun getInputPosition(): Pair<Int, Char> = inputView.inputPosition(board.turn, board)
}
