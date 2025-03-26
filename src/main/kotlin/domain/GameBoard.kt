package domain

import domain.player.Player
import domain.position.Position
import domain.stone.Stone
import domain.stone.StoneColor
import domain.stone.Stones
import exception.RuleViolationException
import rule.lib.type.Violation

class GameBoard(
    private val players: ArrayDeque<Player>,
) {
    private val stones = Stones()

    fun putStone(onPositionReceived: (StoneColor, Stone?) -> Position): Result<Unit> {
        val position = onPositionReceived(players.currentPlayer().stoneColor, stones.lastStone())
        val stone = Stone.of(position, players.currentPlayer().stoneColor)

        val violationType = violation(stone)
        if (violationType.isNone()) {
            stones.add(stone)
            return Result.success(Unit)
        }

        return Result.failure(RuleViolationException(violationType))
    }

    fun placedAllStones(): List<Stone> = stones.value

    fun winner(): StoneColor = players.currentPlayer().stoneColor

    fun nextTurn() {
        val turnOveredPlayer = players.removeFirst()
        players.addLast(turnOveredPlayer)
    }

    fun gameOver(): Boolean {
        with(players.currentPlayer()) {
            return isWin(
                playerStones = stones.playerStones(stoneColor),
                otherStones = stones.otherStones(stoneColor),
                placedStone = stones.lastStone() ?: return false,
            )
        }
    }

    private fun violation(stone: Stone): Violation =
        with(players.currentPlayer()) {
            violation(
                playerStones = stones.playerStones(stoneColor),
                otherStones = stones.otherStones(stoneColor),
                stone,
            )
        }

    private fun ArrayDeque<Player>.currentPlayer(): Player = this.first()
}
