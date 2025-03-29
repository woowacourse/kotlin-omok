package woowacourse.omok.domain

import woowacourse.omok.domain.exception.RuleViolationException
import woowacourse.omok.domain.player.Player
import woowacourse.omok.domain.position.Position
import woowacourse.omok.domain.rule.lib.type.Violation
import woowacourse.omok.domain.stone.Stone
import woowacourse.omok.domain.stone.StoneColor
import woowacourse.omok.domain.stone.Stones

class GameBoard(
    private val players: ArrayDeque<Player>,
) {
    private val stones = Stones()

    fun putStone(onPositionReceived: (StoneColor, Stone?) -> Position): Result<StoneColor> {
        val position = onPositionReceived(players.currentPlayer().stoneColor, stones.lastStone())
        val stone = Stone.of(position, players.currentPlayer().stoneColor)
        val violationType = violation(stone)
        if (violationType.isNone()) {
            stones.add(stone)
            return Result.success(players.currentPlayer().stoneColor)
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

    fun restoreStones(existedStones: List<Stone>) {
        existedStones.forEach { stone -> stones.add(stone) }
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
