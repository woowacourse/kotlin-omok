package domain.player

import domain.rule.OmokRule
import domain.stone.Stone
import domain.stone.StoneColor

data class Players private constructor(private val players: List<Player>, private val rule: OmokRule) {
    val isRunning: Boolean
        get() = players.all { it.canPlace() }

    val isFouling: Boolean
        get() = players.any { it.isFoul() }

    constructor(blackPlayer: Player, whitePlayer: Player, rule: OmokRule) : this(
        listOf(blackPlayer.clone(), whitePlayer.clone()), rule
    )

    fun putStone(stoneColor: StoneColor, stone: Stone): Players {
        val whiteStones = getWhitePlayer().getAllStones()
        val blackStones = getBlackPlayer().getAllStones()

        return when (stoneColor) {
            StoneColor.BLACK -> {
                Players(
                    blackPlayer = getBlackPlayer().putStone(stone, whiteStones, rule),
                    whitePlayer = getWhitePlayer(),
                    rule,
                )
            }

            StoneColor.WHITE -> {
                Players(
                    blackPlayer = getBlackPlayer(),
                    whitePlayer = getWhitePlayer().putStone(stone, blackStones, rule),
                    rule,
                )
            }
        }
    }

    fun getBlackPlayer(): Player = players.first { it is BlackPlayer }

    fun getWhitePlayer(): Player = players.first { it is WhitePlayer }

    fun canPlace(stone: Stone): Boolean = players.none { it.isPlaced(stone) }

    fun getFoulPlayerStoneColor(): StoneColor = players.first { it.isFoul() }.stoneColor
}
