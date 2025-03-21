package omok.domain

import omok.domain.player.BlackPlayer
import omok.domain.player.Player
import omok.domain.player.WhitePlayer
import rule.wrapper.point.Point

class OmokGame(val grid: OmokGrid) {
    private val blackPlayer: BlackPlayer = BlackPlayer(grid.width, grid.height)
    private val whitePlayer: WhitePlayer = WhitePlayer(grid.width, grid.height)

    fun getStartingPlayer(): Player {
        return blackPlayer
    }

    fun isBoardFull(): Boolean {
        return grid.isFull()
    }

    fun playMove(
        player: Player,
        point: Point,
    ) {
        grid.putStone(point, StoneState.getColor(player))
        player.addStone(point)
    }

    fun getOtherPlayer(player: Player): Player {
        return if (player is BlackPlayer) {
            whitePlayer
        } else {
            blackPlayer
        }
    }

    fun validatePoint(
        nowPlayer: Player,
        point: Point,
    ) {
        val otherPlayer = getOtherPlayer(nowPlayer)
        nowPlayer.validateRenjuRule(otherPlayer.stones, point)
        grid.validateEmptyPoint(point)
    }
}
