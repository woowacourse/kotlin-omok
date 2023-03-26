package domain.player

import domain.point.Point
import domain.stone.StoneColor

class Players(private val latestPlayer: Player, private val players: List<Player>) {
    val isPlaying: Boolean
        get() = players.all { it.isPlaying }
    val isFoul: Boolean
        get() = players.any { it.isFoul }
    val curPlayerColor: StoneColor
        get() = players.first { it != latestPlayer }.getStoneColor()

    init {
        require(players.size == PLAYER_SIZE) { INVALID_PLAYERS_SIZE_ERROR_MESSAGE }
    }

    private fun nextPlayer(): Player = players.first { it != latestPlayer }

    fun putStone(point: Point): Players {
        if (players.any { it.isPlaced(point) }) return this

        val otherStones = latestPlayer.getAllPoints()
        val nextLatestPlayer = nextPlayer().putStone(point, otherStones)
        val newPlayers = players.filter { it.getStoneColor() != nextLatestPlayer.getStoneColor() } + nextLatestPlayer
        return Players(nextLatestPlayer, newPlayers)
    }

//    fun getWinner(): Player? = players.find { !it.isFoul && !it.isPlaying }

    fun getLastPoint(): Point? = latestPlayer.getLastStone()

    fun toList(): List<Player> = players.toList()

    fun copy(): Players = Players(latestPlayer, players)

    companion object {
        private const val PLAYER_SIZE = 2
        private const val INVALID_PLAYERS_SIZE_ERROR_MESSAGE = "플레이어는 ${PLAYER_SIZE}명이어야 합니다."
    }
}
