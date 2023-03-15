package player

import BoardState
import Position.Companion.POSITION_RANGE
import Stone

class Board(blackPlayer: Player, whitePlayer: Player) {
    private val players: Players = Players(blackPlayer, whitePlayer)

    private fun canPlace(stone: Stone): Boolean = allPlayers().none { it.isPlaced(stone) }

    private fun allPlayers(): List<Player> = players.toList()

    fun putStone(turn: Turn, stone: Stone): Boolean {
        if (canPlace(stone)) {
            players.putStone(turn, stone)
            return true
        }
        return false
    }

    private fun makeEmptyBoard(): List<MutableList<BoardState>> =
        List(POSITION_RANGE.max()) { MutableList(POSITION_RANGE.max()) { BoardState.EMPTY } }
}
