package woowacourse.omok.domain.model

import woowacourse.omok.domain.model.state.Finished
import woowacourse.omok.domain.model.state.GameState
import woowacourse.omok.domain.model.state.InitialGameTurn
import woowacourse.omok.domain.model.state.InvalidPosition
import woowacourse.omok.domain.model.state.RunningTurn

class OmokGame(
    private val board: Board,
) {
    private val initialTurn: GameState = InitialGameTurn()
    private var currentGameTurn: GameState = InitialGameTurn()

    fun runGame(
        firstPosition: () -> Position,
        nextPosition: (GameState) -> Position,
        handling: (StonePosition, String) -> Unit,
        nextStonePositionResult: () -> Unit,
    ): GameState {
        var gameTurn: GameState = runFirstTurn(firstPosition, nextStonePositionResult)
        while (!gameTurn.finished()) {
            gameTurn = gameTurn(gameTurn, nextPosition, handling, nextStonePositionResult)
        }
        return gameTurn
    }


    fun gameTurn2(
        nextPosition: (GameState) -> Position,
        handling: (StonePosition, String) -> Unit,
        nextStonePositionCallback: (GameState) -> Unit,
        finishedResultCallback: (GameState) -> Unit,
    ): GameState {
        if (currentGameTurn.running()) {
            currentGameTurn = currentGameTurn.place(board, nextPosition(currentGameTurn))
            if (checkInvalidPosition(handling)) return currentGameTurn
            nextStonePositionCallback(currentGameTurn)
            if (checkFinish(finishedResultCallback)) return currentGameTurn
            return currentGameTurn
        }
        if (checkInvalidPosition(handling)) return currentGameTurn
        if (checkFinish(finishedResultCallback)) return currentGameTurn

        return currentGameTurn
    }

    private fun checkFinish(finishedResult: (GameState) -> Unit): Boolean {
        if (currentGameTurn.finished()) {
            finishedResult(currentGameTurn)
            return true
        }
        return false
    }

    private fun checkInvalidPosition(handling: (StonePosition, String) -> Unit): Boolean {
        if (currentGameTurn.invalidPosition()) {
            currentGameTurn = currentGameTurn.handleInvalidPosition(handling)
            return true
        }
        return false
    }

    fun gameTurn(
        gameState: GameState,
        nextPosition: (GameState) -> Position,
        handling: (StonePosition, String) -> Unit,
        nextStonePositionResult: () -> Unit,
    ): GameState {
        var nextGameState = gameState
        nextGameState =
            when (nextGameState) {
                is RunningTurn -> nextGameState.place(board, nextPosition(nextGameState))
                is InvalidPosition -> nextGameState.handleInvalidPosition(handling)
                is Finished -> throw IllegalStateException("게임이 종료되었습니다.")
            }
        nextStonePositionResult()
        return nextGameState
    }

    private fun runFirstTurn(
        firstPosition: () -> Position,
        nextStonePositionResult: () -> Unit,
    ): GameState {
        val gameState = initialTurn.place(board, firstPosition())
        nextStonePositionResult()
        return gameState
    }
}
