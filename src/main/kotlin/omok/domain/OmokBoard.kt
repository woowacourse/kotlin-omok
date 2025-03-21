package omok.domain

class OmokBoard(val width: Int = DEFAULT_SIZE, val height: Int = DEFAULT_SIZE) {
    val positions: List<Position> = (1..15).flatMap { x -> (1..15).map { y -> Position(x, y) } }

    fun canPlace(position: Position) {
        if (findPoint(position.x, position.y)?.stoneState != StoneState.BLANK) {
            throw IllegalArgumentException(
                ERROR_STONE_ALREADY_PUT,
            )
        }
    }

    fun findPoint(
        x: Int,
        y: Int,
    ): Position? {
        return positions.find { it.x == x && it.y == y }
    }

    fun putStone(
        position: Position,
        state: StoneState,
    ) {
        findPoint(position.x, position.y)?.changeState(state)
    }

    companion object {
        const val DEFAULT_SIZE: Int = 15
        private const val ERROR_STONE_ALREADY_PUT = "이미 돌이 있습니다."
    }
}
