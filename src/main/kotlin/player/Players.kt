package player

import Stone

class Players private constructor(private val players: Map<Turn, Player>) {
    constructor(black: Player, white: Player) : this(mapOf(Turn.BLACK to black, Turn.WHITE to white))

    fun toList(): List<Player> = players.values.toList()

    fun putStone(turn: Turn, stone: Stone) {
        players[turn]?.putStone(stone)
    }
}
