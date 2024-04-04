package woowacourse.omok.domain.controller

import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.OmokGame
import woowacourse.omok.domain.model.Position
import woowacourse.omok.domain.model.state.AlreadyHaveStone
import woowacourse.omok.domain.model.state.ForbiddenPosition
import woowacourse.omok.domain.model.state.GameState
import woowacourse.omok.domain.view.InputView
import woowacourse.omok.domain.view.OutputView

class OmokController(
    private val inputView: InputView,
    private val outputView: OutputView,
) {
    val board = initializedBoard()
    private val omokGame = OmokGame(board)

    private val nextPositionListener: NextPositionListener = initNextPositionListener()
    private val invalidPositionHandler = initInvalidPositionHandler()
    private val finishedObserver = initFinishedObserver()

    fun startGame() {
        do {
            val gameState = omokGame.gameTurn(nextPositionListener, invalidPositionHandler, finishedObserver)
        } while (!gameState.finished())
    }

    private fun initializedBoard(): Board {
        return Board().apply { outputView.printInitialGuide(this) }
    }

    private fun initNextPositionListener() = object : NextPositionListener {
        override fun nextPosition(gameState: GameState): Position {
            return inputView.readStonePosition(
                gameState.latestStone().nextOrFirst(),
                gameState.latestPosition(),
            )
        }

        override fun nextStonePositionCallback(gameState: GameState) = outputView.printBoard(board)
    }

    private fun initInvalidPositionHandler() = InvalidPositionHandler { inValidStonePosition, invalidPositionState ->
        when (invalidPositionState) {
            is ForbiddenPosition -> outputView.printInvalidPosition(inValidStonePosition, "는 금수 규칙에 따라 둘 수 없습니다")
            is AlreadyHaveStone -> outputView.printInvalidPosition(
                inValidStonePosition,
                "는 이미 돌이 있는 위치이므로 둘 수 없습니다."
            )
        }
    }

    private fun initFinishedObserver() = FinishedObserver { gameState ->
        outputView.printWinner(gameState.latestStone())
    }
}
