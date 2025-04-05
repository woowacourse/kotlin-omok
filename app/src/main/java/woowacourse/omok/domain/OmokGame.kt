package woowacourse.omok.domain

import woowacourse.omok.domain.player.Player
import woowacourse.omok.domain.rule.lib.type.Violation
import woowacourse.omok.domain.stone.Stone
import woowacourse.omok.domain.stone.StoneColor
import woowacourse.omok.domain.stone.Stones

class OmokGame(
    private val players: ArrayDeque<Player>,
) {
    fun turnColor(): StoneColor {
        return players.first().stoneColor
    }

    fun lastStone(gameBoard: GameBoard): Stone? {
        return gameBoard.stones.lastStone()
    }

    fun changeTurn() {
        val turnOveredPlayer = players.removeFirst()
        players.addLast(turnOveredPlayer)
    }

    fun currentPlayer(): Player {
        return players.first()
    }

    fun violation(
        stone: Stone,
        stones: Stones,
        player: Player,
    ): Violation =
        with(player) {
            violation(
                playerStones = stones.playerStones(stoneColor),
                otherStones = stones.otherStones(stoneColor),
                stone,
            )
        }

    fun adjustPlayerTurn(stone: Stone) {
        if (stone.color == StoneColor.BLACK) changeTurn()
    }

    fun gameOver(gameBoard: GameBoard): Boolean {
        val player = players.first()
        val stones = gameBoard.stones
        with(player) {
            return isWin(
                playerStones = stones.playerStones(stoneColor),
                otherStones = stones.otherStones(stoneColor),
                placedStone = stones.lastStone() ?: return false,
            )
        }
    }
}
