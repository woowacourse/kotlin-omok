package domain.board

import domain.player.Player
import domain.player.Players
import domain.rule.OmokRule
import domain.stone.Stone
import domain.stone.StoneColor

class Board(private val players: Players) {
    constructor(blackPlayer: Player, whitePlayer: Player, rule: OmokRule) : this(Players(blackPlayer, whitePlayer, rule))

    fun putStone(stoneColor: StoneColor, stone: Stone): Board? {
        if (players.canPlace(stone)) {
            return Board(players.putStone(stoneColor, stone))
        }
        return null
    }

    fun getPlayers(): Players = players.copy()

    fun isRunning(): Boolean = players.isRunning

    fun isLose(): Boolean = players.isBlackLose
}
