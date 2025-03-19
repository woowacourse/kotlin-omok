package omok.domain

import omok.util.retryInput
import rule.wrapper.point.Point

class OmokGame {
    val grid: OmokGrid = OmokGrid()
    private val blackPlayer: BlackPlayer = BlackPlayer()
    private val whitePlayer: WhitePlayer = WhitePlayer()

    fun playGame(
        onTurnStarted: (List<MutableList<StoneState>>) -> Unit,
        onSelectPosition: (Player, Point?) -> Point,
    ): OmokResult {
        var latestPoint: Point? = null
        var nowPlayer: Player = blackPlayer
        while (true) {
            latestPoint = turn(nowPlayer, latestPoint, onTurnStarted, onSelectPosition)
            if (nowPlayer.checkWin(latestPoint)) return OmokResult.returnWinner(nowPlayer)
            if (grid.isFull()) break
            nowPlayer = getOtherPlayer(nowPlayer)
        }
        return OmokResult.DRAW
    }

    private fun turn(
        player: Player,
        latestPoint: Point?,
        onTurnStarted: (List<MutableList<StoneState>>) -> Unit,
        onSelectPosition: (Player, Point?) -> Point,
    ): Point {
        return retryInput {
            onTurnStarted(grid.board)
            val point = onSelectPosition(player, latestPoint)
            validatePosition(player, point)
            grid.putStone(point, getStoneState(player))
            player.addStone(point)
            point
        }
    }

    private fun validatePosition(
        nowPlayer: Player,
        point: Point,
    ) {
        val otherPlayer = getOtherPlayer(nowPlayer)
        if (nowPlayer.isViolation(otherPlayer.stones, point)) throw IllegalStateException(ERROR_WRONG_POSITION)
    }

    private fun getOtherPlayer(player: Player): Player {
        return if (player is BlackPlayer) {
            whitePlayer
        } else {
            blackPlayer
        }
    }

    private fun getStoneState(player: Player): StoneState {
        return if (player is BlackPlayer) {
            StoneState.BLACK
        } else {
            StoneState.WHITE
        }
    }

    companion object {
        private const val ERROR_WRONG_POSITION = "해당 위치에 놓을 수 없습니다"
    }
}
