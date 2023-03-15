import Position.Companion.POSITION_RANGE
import player.Player
import player.Players

class Board(private val players: Players) {
    constructor(blackPlayer: Player, whitePlayer: Player) : this(Players(blackPlayer, whitePlayer))

    fun putStone(turn: Turn, stone: Stone): Player? {
        if (players.canPlace(stone)) {
            return players.putStone(turn, stone)
        }
        return null
    }

    private fun makeEmptyBoard(): List<MutableList<BoardState>> =
        List(POSITION_RANGE.max()) { MutableList(POSITION_RANGE.max()) { BoardState.EMPTY } }
}
