package woowacourse.omok.domain.controller

import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.OmokGame
import woowacourse.omok.domain.view.InputView
import woowacourse.omok.domain.view.OutputView

class OmokController(
    private val inputView: InputView,
    private val outputView: OutputView,
) {
    fun startGame() {
        val board = initializedBoard()
        val omokGame = OmokGame(board)

        while (!omokGame.currentGameTurn.finished()) {
            omokGame.gameTurn(
                nextPosition = { gameState -> inputView.readStonePosition(gameState.latestStone().nextOrFirst(), gameState.latestPosition(),) },
                handling = { inValidStonePosition -> outputView.printInvalidPosition(inValidStonePosition, "have to remove") },
                nextStonePositionCallback = { outputView.printBoard(board) },
                finishedResultCallback = {gameState -> outputView.printWinner(gameState.latestStone())}
            )
        }
    }

    private fun initializedBoard(): Board {
        return Board().apply { outputView.printInitialGuide(this) }
    }
}
