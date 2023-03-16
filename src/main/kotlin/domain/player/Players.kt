package domain.player

import domain.stone.Stone
import domain.stone.StoneColor

data class Players private constructor(private val players: List<Player>) {
    val isRunning: Boolean
        get() = players.all { it.canPlace() }

    constructor(blackPlayer: Player, whitePlayer: Player) : this(listOf(blackPlayer.clone(), whitePlayer.clone()))

    fun putStone(stoneColor: StoneColor, stone: Stone): Players {
        if (stoneColor == StoneColor.BLACK) {
            return Players(blackPlayer = getBlackPlayer().putStone(stone, getWhitePlayer().getAllStones()), whitePlayer = getWhitePlayer())
        }
        return Players(blackPlayer = getBlackPlayer(), whitePlayer = getWhitePlayer().putStone(stone, getBlackPlayer().getAllStones()))
    }

    fun getBlackPlayer(): Player = players.first { it is BlackPlayer }

    fun getWhitePlayer(): Player = players.first { it is WhitePlayer }

    fun canPlace(stone: Stone): Boolean = players.none { it.isPlaced(stone) }
}
