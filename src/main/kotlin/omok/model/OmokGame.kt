package omok.model

import omok.controller.ValidPosition

class OmokGame2(
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
        board.place2(nextPosition, recentPlayer)
        return nextPosition
    }
}

class OmokGame(private val board: Board, private val players: Players) {
    fun gameWinner(
        nextStonePosition: (Player, Position?) -> Position,
        nextStonePositionResult: () -> Unit,
        handleException: (Exception) -> Unit,
    ): Player {
        var recentPlayer = players.firstOrderedPlayer()
        var recentPosition: Position? = null

        while (true) {
            recentPosition = recentPosition.next(recentPlayer, nextStonePosition, handleException)
            nextStonePositionResult()
            if (recentPlayer.isWin(board, recentPosition)) break
            recentPlayer = players.nextOrder(recentPlayer)
        }
        return recentPlayer
    }

    private fun Position?.next(
        recentPlayer: Player,
        nextStonePosition: (Player, Position?) -> Position,
        handleException: (Exception) -> Unit,
    ) = retryUntilNotException(
        block = {
            val nextPosition = nextStonePosition(recentPlayer, this)
            board.place(nextPosition, recentPlayer)
            nextPosition
        },
        handleException,
    )
}

private fun <T> retryUntilNotException(
    block: () -> (T),
    handleException: (Exception) -> Unit,
): T {
    return try {
        block()
    } catch (e: IllegalArgumentException) {
        handleException(e)
        retryUntilNotException(block, handleException)
    } catch (e: IllegalStateException) {
        handleException(e)
        retryUntilNotException(block, handleException)
    }
}
