import player.Player
import player.Players

class Board(private val players: Players) {
    constructor(blackPlayer: Player, whitePlayer: Player) : this(Players(blackPlayer, whitePlayer))

    fun putStone(stoneColor: StoneColor, stone: Stone): Board? {
        if (players.canPlace(stone)) {
            return Board(players.putStone(stoneColor, stone))
        }
        return null
    }

    fun getPlayers(): Players = players.copy()

    fun isRunning(): Boolean = players.isRunning

    fun getWinner(): Player = players.getWinner()

    // private fun makeEmptyBoard(): List<MutableList<BoardState>> =
    //     List(POSITION_RANGE.max()) { MutableList(POSITION_RANGE.max()) { BoardState.EMPTY } }
}
