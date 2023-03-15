package player

import BoardState
import Position.Companion.POSITION_RANGE
import Stone

class Board(private val players: Players) {
    constructor(blackPlayer: Player, whitePlayer: Player) : this(Players(blackPlayer, whitePlayer))

    private fun canPlace(stone: Stone): Boolean = allPlayers().none { it.isPlaced(stone) }

    private fun allPlayers(): List<Player> = players.toList()

    fun putStone(turn: Turn, stone: Stone): Player? {
        if (canPlace(stone)) {
            return players.putStone(turn, stone)
        }
        return null
    }

    private fun makeEmptyBoard(): List<MutableList<BoardState>> =
        List(POSITION_RANGE.max()) { MutableList(POSITION_RANGE.max()) { BoardState.EMPTY } }
}
