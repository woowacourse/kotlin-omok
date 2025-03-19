package omok.domain

import omok.controller.minus
import omok.util.retryInput
import rule.wrapper.point.Point

class OmokGame {
    val grid: OmokGrid = OmokGrid()
    val blackPlayer: BlackPlayer = BlackPlayer()
    val whitePlayer: WhitePlayer = WhitePlayer()

    fun playGame(
        onTurnStarted: (List<MutableList<StoneState>>) -> Unit,
        onSelectPosition: (Player, Point?) -> Point,
    ): OmokResult {
        var latestPoint: Point? = null
        var nowPlayer: Player = blackPlayer
        while (true) {
            latestPoint = turn(nowPlayer, latestPoint, onTurnStarted, onSelectPosition)
            if (checkOmok(latestPoint)) return OmokResult.returnWinner(nowPlayer)
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
            val point = onSelectPosition(player, latestPoint).minus(1)
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

    fun checkOmok(point: Point): Boolean {
        val directions: List<Direction> =
            listOf(
                Direction(0, 1),
                Direction(1, 0),
                Direction(1, 1),
                Direction(1, -1),
            )

        return directions.any { dir ->
            val count = search(dir, point) + search(-dir, point) - 1
            count >= OMOK_STANDARD
        }
    }

    private fun search(
        direction: Direction,
        point: Point,
    ): Int {
        val coordinateX = point.row
        val coordinateY = point.col
        val state = grid.board[coordinateX][coordinateY]
        var count = DEFAULT_COUNT

        while (checkRange(coordinateX + direction.rowDelta * count, coordinateY + direction.colDelta * count) &&
            grid.board[coordinateX + direction.rowDelta * count][coordinateY + direction.colDelta * count] == state
        ) {
            count++
        }

        return count
    }

    private fun checkRange(
        coordinateX: Int,
        coordinateY: Int,
    ): Boolean {
        return coordinateX in (MIN_BOUND..MAX_BOUND) && coordinateY in (MIN_BOUND..MAX_BOUND)
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
        private const val DEFAULT_COUNT: Int = 0
        private const val OMOK_STANDARD: Int = 5
        const val MIN_BOUND = 0
        const val MAX_BOUND = 14

        private const val ERROR_WRONG_POSITION = "해당 위치에 놓을 수 없습니다"
    }
}
