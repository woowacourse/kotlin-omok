package woowacourse.omok.domain.model

import woowacourse.omok.domain.controller.ValidPosition
import woowacourse.omok.domain.model.state.Finished
import woowacourse.omok.domain.model.state.GameState
import woowacourse.omok.domain.model.state.InitialGameTurn
import woowacourse.omok.domain.model.state.InvalidPosition
import woowacourse.omok.domain.model.state.Turn

class OmokGame(
    private val board: Board,
    private val players: Players,
    private val validPosition: List<ValidPosition>,
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

    fun gameWinner(
        nextStonePosition: (Player, Position?) -> Position,
        nextStonePositionResult: () -> Unit,
    ): Player {
        var recentPlayer = players.firstOrderedPlayer()
        var recentPosition: Position? = null

        while (true) {
            val nextPosition = nextPosition(recentPosition, recentPlayer, nextStonePosition, nextStonePositionResult)
            if (nextPosition == null || nextPosition == recentPosition) continue
            recentPosition = nextPosition
            if (recentPlayer.isWin(board, recentPosition)) break
            recentPlayer = players.nextOrder(recentPlayer)
        }
        return recentPlayer
    }

    fun nextPosition(
        currentPosition: Position?,
        recentPlayer: Player,
        nextStonePosition: (Player, Position?) -> Position,
        nextStonePositionResult: () -> Unit,
    ): Position? {
        val nextPosition = nextStonePosition(recentPlayer, currentPosition)
        if (validPosition.any { !it.valid(board, nextPosition, recentPlayer) }) {
            return currentPosition
        }
        board.place(nextPosition, recentPlayer)
        nextStonePositionResult()
        return nextPosition
    }
}
