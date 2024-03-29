package omok.model

import omok.controller.ValidPosition

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
            val next = recentPosition.next(recentPlayer, nextStonePosition)
            if (next == recentPosition || next == null) {
                continue
            }
            recentPosition = next
            nextStonePositionResult()
            if (recentPlayer.isWin(board, recentPosition)) break
            recentPlayer = players.nextOrder(recentPlayer)
        }
        return recentPlayer
    }

    private fun Position?.next(
        recentPlayer: Player,
        nextStonePosition: (Player, Position?) -> Position,
    ): Position? {
        val nextPosition = nextStonePosition(recentPlayer, this)
        if (validPosition.any { !it.valid(board, nextPosition, recentPlayer) }) {
            return this // 현 위치ㅣ 리턴
        }
        board.place(nextPosition, recentPlayer)
        return nextPosition
    }
}
