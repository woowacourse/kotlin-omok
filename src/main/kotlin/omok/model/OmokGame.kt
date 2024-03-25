package omok.model

import omok.controller.retryUntilNotException
import omok.model.rule.winning.WinningCondition

class OmokGame(
    private val board: Board,
    private val omokPlayers: OmokPlayers,
) {
    fun gameResult(
        omokTurnAction: OmokTurnAction,
        winningCondition: WinningCondition,
    ): FinishType {
        var recentPlayer = omokPlayers.firstOrderPlayer()
        var recentPosition: Position? = null

        while (true) {
            recentPosition = recentPosition.next(omokTurnAction, recentPlayer)
            omokTurnAction.onStonePlace(board)
            if (winningCondition.isWin(board, recentPosition)) break
            if (board.isFull()) return FinishType.DRAW
            recentPlayer = omokPlayers.next(recentPlayer)
        }
        return omokPlayers.winningFinishType(recentPlayer)
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
}
