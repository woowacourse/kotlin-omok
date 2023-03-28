package domain.player

import domain.point.Point
import domain.stone.StoneColor

class Players(private val latestPlayer: Player, private val players: List<Player>) {
    val isPlaying: Boolean
        get() = players.all { it.isPlaying }
    val isFoul: Boolean
        get() = players.any { it.isFoul }
    val curPlayerColor: StoneColor
        get() = players.first { it != latestPlayer }.getColor()

    init {
        require(players.size == PLAYER_SIZE) { INVALID_PLAYERS_SIZE_ERROR_MESSAGE }
    }

    private fun nextPlayer(): Player = players.first { it != latestPlayer }

    fun putPoint(point: Point): Players {
        if (players.any { it.isPlaced(point) }) return this

        val otherPoints = latestPlayer.getAllPoints()
        val nextLatestPlayer = nextPlayer().putPoint(point, otherPoints)
        val newPlayers = players.filter { it.getColor() != nextLatestPlayer.getColor() } + nextLatestPlayer
        return Players(nextLatestPlayer, newPlayers)
    }

    fun getLastPoint(): Point? = latestPlayer.getLastPoint()

    fun toList(): List<Player> = players.toList()

    fun copy(): Players = Players(latestPlayer, players)

    companion object {
        private const val PLAYER_SIZE = 2
        private const val INVALID_PLAYERS_SIZE_ERROR_MESSAGE = "플레이어는 ${PLAYER_SIZE}명이어야 합니다."

        fun from(blackPlayer: BlackPlayer, whitePlayer: WhitePlayer): Players {
            if (blackPlayer.getPointSize() == whitePlayer.getPointSize()) return Players(whitePlayer, listOf(blackPlayer, whitePlayer))
            return Players(blackPlayer, listOf(blackPlayer, whitePlayer))
        }
    }
}
