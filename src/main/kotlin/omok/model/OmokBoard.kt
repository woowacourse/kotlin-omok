package omok.model

class OmokBoard(
//    private val stones: Stones,
) {
    private val board = mutableMapOf<Position, StoneState>()

    init {
        for (x in 1..15) {
            for (y in 1..15) {
                val position = Position(X(x), Y(y))
                board[position] = StoneState.NONE
            }
        }
    }

    fun canPlaceStone(position: Position): Boolean = board[position] == StoneState.NONE
}
