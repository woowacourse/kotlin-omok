package omok.domain

class Board(val blackPlayer: Player, val whitePlayer: Player) {
    private val positions = Position.POSITIONS.toList()

    fun isPlaceable(position: Position): Boolean {
        return positions[positions.indexOf(position)].isEmpty()
    }

    fun putStone(position: Position) {
        positions[positions.indexOf(position)].occupy()
    }
}
