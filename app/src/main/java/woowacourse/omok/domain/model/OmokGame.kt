package woowacourse.omok.domain.model

import woowacourse.omok.domain.controller.FinishedObserver
import woowacourse.omok.domain.controller.InvalidPositionHandler
import woowacourse.omok.domain.controller.NextPositionListener
import woowacourse.omok.domain.model.state.Finished
import woowacourse.omok.domain.model.state.GameState
import woowacourse.omok.domain.model.state.InitialGameTurn
import woowacourse.omok.domain.model.state.InvalidPositionState
import woowacourse.omok.domain.model.state.Running

class OmokGame(
    private val board: Board,
) {
    private var currentGameTurn: GameState = InitialGameTurn()

    fun gameTurn(
        nextPositionListener: NextPositionListener,
        invalidPositionHandler: InvalidPositionHandler,
        finishedObserver: FinishedObserver,
    ): GameState {
        val position = nextPositionListener.nextPosition(currentGameTurn)

        when (currentGameTurn) {
            is Running -> {
                currentGameTurn = currentGameTurn.place(board, position)

                if (currentGameTurn is InvalidPositionState) return handleInvalidPosition(invalidPositionHandler)

                nextPositionListener.nextStonePositionCallback(currentGameTurn)
                if (currentGameTurn is Finished) return finish(finishedObserver)
                return currentGameTurn
            }

            is Finished -> return finish(finishedObserver)
            is InvalidPositionState -> return handleInvalidPosition(invalidPositionHandler)
        }
    }

    private fun handleInvalidPosition(handler: InvalidPositionHandler): GameState =
        currentGameTurn.let {
            currentGameTurn = currentGameTurn.handleInvalidPosition(handler)
            currentGameTurn
        }

    private fun finish(finishedObserver: FinishedObserver): GameState =
        currentGameTurn.apply {
            finishedObserver.finishedCallback(this)
        }
}
