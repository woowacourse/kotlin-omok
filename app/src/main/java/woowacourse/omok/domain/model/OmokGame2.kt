package woowacourse.omok.domain.model

import woowacourse.omok.domain.model.state.Finished
import woowacourse.omok.domain.model.state.GameState
import woowacourse.omok.domain.model.state.InitialGameTurn
import woowacourse.omok.domain.model.state.InvalidPosition
import woowacourse.omok.domain.model.state.Turn

class OmokGame2(
    private val board: Board,
) {
    private val initialTurn: GameState = InitialGameTurn()

    fun runGame(
        firstPosition: () -> Position,
        nextPosition: (GameState) -> Position,
        handling: (StonePosition, String) -> Unit,
        nextStonePositionResult: () -> Unit,
    ): GameState {
        var gameTurn: GameState = runFirstTurn(firstPosition)
        nextStonePositionResult()
        while (!gameTurn.isFinished()) {
            gameTurn =
                when (gameTurn) {
                    is InitialGameTurn -> gameTurn.place(board, nextPosition(gameTurn))
                    is Turn -> gameTurn.place(board, nextPosition(gameTurn))
                    is InvalidPosition -> gameTurn.handleInvalidPosition(handling)
                    is Finished -> throw IllegalStateException("게임이 종료되었습니다.")
                }
            nextStonePositionResult()
        }
        return gameTurn
    }

    private fun runFirstTurn(firstPosition: () -> Position): GameState = initialTurn.place(board, firstPosition())
}