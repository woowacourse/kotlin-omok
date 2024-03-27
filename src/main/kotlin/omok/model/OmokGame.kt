package omok.model

class OmokGame(private val board: Board, private val players: Players) {
    fun gameWinner(
        nextStonePosition: (Player, Position?) -> Position,
        nextStonePositionResult: () -> Unit,
    ): Player {
        var recentPlayer = players.firstOrderedPlayer()
        var recentPosition: Position? = null

        while (true) {
            recentPosition = recentPosition.next(recentPlayer, nextStonePosition)
            nextStonePositionResult.invoke()
            if (recentPlayer.isWin(board, recentPosition)) break
            recentPlayer = players.nextOrder(recentPlayer)
        }
        return recentPlayer
    }

    private fun Position?.next(
        recentPlayer: Player,
        nextStonePosition: (Player, Position?) -> Position,
    ) = retryUntilNotException {
        val nextPosition = nextStonePosition(recentPlayer, this)
        board.place(nextPosition, recentPlayer)
        nextPosition
    }
}

fun <T> retryUntilNotException(block: () -> (T)): T {
    return try {
        block()
    } catch (e: IllegalArgumentException) {
        println(e.localizedMessage)
        retryUntilNotException(block)
    }
}
