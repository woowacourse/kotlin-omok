package omok.model

import omok.controller.retryUntilNotException
import omok.model.rule.finish.FinishCondition

class OmokGame(
    private val board: Board,
    private val omokPlayers: OmokPlayers,
    private val finishConditions: List<FinishCondition>,
) {
    fun gameResult(omokTurnAction: OmokTurnAction): FinishType {
        var recentPlayer = omokPlayers.firstOrderPlayer()
        var recentPosition: Position? = null

        while (true) {
            recentPosition = recentPosition.next(omokTurnAction, recentPlayer)
            omokTurnAction.onStonePlace(board)
            val finishType = finishType(board, recentPosition, recentPlayer)
            if (finishType.isFinish()) return finishType
            recentPlayer = omokPlayers.next(recentPlayer)
        }
    }

    private fun Position?.next(
        omokTurnAction: OmokTurnAction,
        recentPlayer: Player,
    ): Position {
        return retryUntilNotException {
            val nextPosition = omokTurnAction.nextStonePosition(recentPlayer.stone, this)
            board.place(nextPosition, recentPlayer)
            nextPosition
        }
    }

    private fun finishType(
        board: Board,
        recentPosition: Position,
        player: Player,
    ): FinishType {
        return finishConditions
            .map { it.finishType(board, recentPosition, player) }
            .minBy { it.ordinal }
    }
}
