package omok.model

class OmokGame(private val board: Board, private val players: Players) {
    fun gameWinner(
        nextStonePosition: (Player2, Position?) -> Position,
        nextStonePositionResult: () -> Unit,
        handleException: (Exception) -> Unit,
    ): Player2 {
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
        recentPlayer: Player2,
        nextStonePosition: (Player2, Position?) -> Position,
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
