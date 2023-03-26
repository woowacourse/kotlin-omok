package domain.player

import domain.point.Point
import domain.stone.StoneColor

data class Players private constructor(private val players: List<Player>) {
    private lateinit var latestPlayer: Player
    val isPlaying: Boolean
        get() = players.all { it.isPlaying }
    val isFoul: Boolean
        get() = players.any { it.isFoul }
    val curPlayerColor: StoneColor
        get() = players.first { it != latestPlayer }.getStoneColor()

    init {
        require(players.size == PLAYER_SIZE) { INVALID_PLAYERS_SIZE_ERROR_MESSAGE }
    }

    constructor(blackPlayer: Player, whitePlayer: Player) :
        this(listOf(blackPlayer, whitePlayer)) {
            if (blackPlayer.getPointSize() == whitePlayer.getPointSize()) {
                latestPlayer = whitePlayer
                return
            }
            latestPlayer = blackPlayer
        }

    private constructor(latestPlayer: Player, players: List<Player>) : this(players.toList()) {
        this.latestPlayer = latestPlayer
    }

    private fun nextPlayer(): Player = players.first { it != latestPlayer }

    fun putStone(point: Point): Players {
        if (players.any { it.isPlaced(point) }) return this

        val otherStones = latestPlayer.getAllPoints()
        val nextLatestPlayer = nextPlayer().putStone(point, otherStones)
        val newPlayers =
            players.filter { it.getStoneColor() != nextLatestPlayer.getStoneColor() } + nextLatestPlayer
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
