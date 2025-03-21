package omok.domain

import omok.domain.player.Player
import omok.util.retryInput

class OmokGame(val grid: OmokGrid) {
    fun playGame(
        onTurnStarted: (List<MutableList<StoneState>>) -> Unit,
        onSelectPosition: (Player, Position?, OmokGrid) -> Position,
    ): OmokResult {
        var latestPoint: Position? = null

        while (true) {
//            latestPoint = playTurn(nowPlayer, latestPoint, onTurnStarted, onSelectPosition)
//            if (nowPlayer.checkWin(latestPoint)) return OmokResult.getWinner(nowPlayer)
//            if (grid.isFull()) break
        }
        return OmokResult.DRAW
    }

    private fun playTurn(
        player: Player,
        latestPoint: Position?,
        onTurnStarted: (List<MutableList<StoneState>>) -> Unit,
        onSelectPosition: (Player, Position?, OmokGrid) -> Position,
    ): Position {
        onTurnStarted(grid.board)
        val point = getPointToPlace(player, latestPoint, onSelectPosition)
        playMove(point,StoneState.BLANK)
        return point
    }

    private fun playMove(
        point: Position,
        state: StoneState
    ) {
        grid.putStone(point,state)
    }

    private fun getPointToPlace(
        player: Player,
        latestPoint: Position?,
        onSelectPosition: (Player, Position?, OmokGrid) -> Position,
    ): Position {
        return retryInput {
            val point = onSelectPosition(player, latestPoint, grid)
//            validatePosition(point)
            grid.canPlace(point)
            point
        }
    }
}
