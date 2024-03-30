package woowacourse.omok.domain.model

import woowacourse.omok.domain.controller.ValidPosition

class OmokGame(
    private val board: Board,
    private val players: Players,
    private val validPosition: List<ValidPosition>,
) {
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
