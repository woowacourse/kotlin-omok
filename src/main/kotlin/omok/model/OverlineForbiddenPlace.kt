package omok.model

class OverlineForbiddenPlace: ForbiddenPlace {
    override fun availablePosition(board: Board, position: Position, stone: Stone): Boolean {
        for (direction in Direction.values()) {
            val count = countConsecutiveStones(board, position, stone, direction) +
                    countConsecutiveStones(board, position, stone, direction.reverse())

            if (count >= 6) {
                return false
            }
        }
        return true
    }

    private fun countConsecutiveStones(
        board: Board,
        position: Position,
        stone: Stone,
        direction: Direction
    ): Int {
        var count = 0
        var currentPos = position
        while (board.find(currentPos) == stone) {
            count++
            currentPos = currentPos.move(direction) ?: break
        }
        return count
    }
}
