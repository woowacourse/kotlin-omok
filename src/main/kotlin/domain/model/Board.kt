package domain.model

class Board(val stones: Map<Position, Stone> = mapOf()) {
    fun placeStone(
        position: Position,
        stone: Stone,
    ): Board {
        require(stones[position] == null) { "이미 있는 곳에는 둘 수 없습니다." }
        return Board(stones + mapOf(position to stone))
    }
}
