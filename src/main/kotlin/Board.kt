class Board(val blackPlayer: Player, val whitePlayer: Player) {
    val positions = Position.POSITIONS

    fun isPlaceable(position: Position): Boolean {
        return positions[positions.indexOf(position)].isEmpty()
    }

    fun putStone(position: Position) {
        positions[positions.indexOf(position)].occupy()
    }
}
