package woowacourse.omok.domain.controller

import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.OmokGame
import woowacourse.omok.domain.model.Stone
import woowacourse.omok.domain.view.InputView
import woowacourse.omok.domain.view.OutputView

class OmokController(
    private val inputView: InputView,
    private val outputView: OutputView,
) {
    fun startGame() {
        val board = initializedBoard()
        val omokGame = OmokGame(board)

        val result = omokGame.runGame(
            { inputView.readFirstStonePosition(Stone.BLACK) },
            { gameState ->
                inputView.readStonePosition(
                    gameState.latestStonePosition().stone.nextOrFirst(),
                    gameState.latestStonePosition().position,
                )
            },
            { inValidStonePosition, message -> outputView.printInvalidPosition(inValidStonePosition, message) },
            { outputView.printBoard(board) },
        )
        outputView.printWinner(result.latestStonePosition().stone)
    }

    private fun initializedBoard(): Board {
        return Board().apply { outputView.printInitialGuide(this) }
    }
}
