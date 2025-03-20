package omok.domain

import omok.domain.player.BlackPlayer
import omok.domain.player.Player
import omok.domain.player.WhitePlayer
import omok.util.retryInput
import rule.wrapper.point.Point

class OmokGame(val grid: OmokGrid) {
    private val blackPlayer: BlackPlayer = BlackPlayer(grid.width, grid.height)
    private val whitePlayer: WhitePlayer = WhitePlayer(grid.width, grid.height)

    fun playGame(
        onTurnStarted: (List<MutableList<StoneState>>) -> Unit,
        onSelectPosition: (Player, Point?, OmokGrid) -> Point,
    ): OmokResult {
        var latestPoint: Point? = null
        var nowPlayer: Player = blackPlayer

        while (true) {
            latestPoint = playTurn(nowPlayer, latestPoint, onTurnStarted, onSelectPosition)
            if (nowPlayer.checkWin(latestPoint)) return OmokResult.getWinner(nowPlayer)
            if (grid.isFull()) break
            nowPlayer = getOtherPlayer(nowPlayer)
        }
        return OmokResult.DRAW
    }

    private fun playTurn(
        player: Player,
        latestPoint: Point?,
        onTurnStarted: (List<MutableList<StoneState>>) -> Unit,
        onSelectPosition: (Player, Point?, OmokGrid) -> Point,
    ): Point {
        onTurnStarted(grid.board)
        val point = getPointToPlace(player, latestPoint, onSelectPosition)
        playMove(player, point)
        return point
    }

    private fun playMove(
        player: Player,
        point: Point,
    ) {
        grid.putStone(point, StoneState.getColor(player))
        player.addStone(point)
    }

    private fun getPointToPlace(
        player: Player,
        latestPoint: Point?,
        onSelectPosition: (Player, Point?, OmokGrid) -> Point,
    ): Point {
        return retryInput {
            val point = onSelectPosition(player, latestPoint, grid)
            validatePosition(player, point)
            grid.canPlace(point)
            point
        }
    }

    private fun validatePosition(
        nowPlayer: Player,
        point: Point,
    ) {
        val otherPlayer = getOtherPlayer(nowPlayer)
        nowPlayer.isViolation(otherPlayer.stones, point)
    }

    private fun getOtherPlayer(player: Player): Player {
        return if (player is BlackPlayer) {
            whitePlayer
        } else {
            blackPlayer
        }
    }
}
