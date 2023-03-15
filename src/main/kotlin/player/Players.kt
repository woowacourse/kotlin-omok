package player

import Stone
import Turn

class Players private constructor(private val players: Map<Turn, Player>) {
    constructor(black: Player, white: Player) : this(mapOf(Turn.BLACK to black, Turn.WHITE to white))

    fun putStone(turn: Turn, stone: Stone): Player? = players[turn]?.putStone(stone)

    fun canPlace(stone: Stone): Boolean = players.values.none { it.isPlaced(stone) }
}
