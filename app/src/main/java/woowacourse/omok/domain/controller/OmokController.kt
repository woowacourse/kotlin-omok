package woowacourse.omok.domain.controller

import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.OmokGame
import woowacourse.omok.domain.model.state.AlreadyHaveStone
import woowacourse.omok.domain.model.state.ForbiddenPosition
import woowacourse.omok.domain.view.InputView
import woowacourse.omok.domain.view.OutputView

class OmokController(
    private val inputView: InputView,
    private val outputView: OutputView,
) {
    fun startGame() {
        val board = initializedBoard()
        val omokGame = OmokGame(board)

        do {
            val gameState = omokGame.gameTurn(
                nextPosition = { gameState ->
                    inputView.readStonePosition(
                        gameState.latestStone().nextOrFirst(),
                        gameState.latestPosition(),
                    )
                },
                handling = { inValidStonePosition, invalidPositionState ->
                    when (invalidPositionState) {
                        is ForbiddenPosition -> outputView.printInvalidPosition(inValidStonePosition, "는 금수 규칙에 따라 둘 수 없습니다")
                        is AlreadyHaveStone -> outputView.printInvalidPosition(inValidStonePosition, "는 이미 돌이 있는 위치이므로 둘 수 없습니다.")
                    }
                },
                nextStonePositionCallback = { outputView.printBoard(board) },
                finishedResultCallback = { gameState -> outputView.printWinner(gameState.latestStone()) }
            )
        } while (!gameState.finished())
    }

    private fun initializedBoard(): Board {
        return Board().apply { outputView.printInitialGuide(this) }
    }
}
