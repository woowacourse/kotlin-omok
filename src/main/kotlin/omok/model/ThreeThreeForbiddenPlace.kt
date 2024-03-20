package omok.model

class ThreeThreeForbiddenPlace : ForbiddenPlace {
    override fun availablePosition(board: Board, position: Position, stone: Stone): Boolean {
        for (direction1 in Direction.values()) {
            val position1 = position.move(direction1) ?: continue
            if (board.find(position1) != Stone.NONE) {
                continue
            }
            for (direction2 in Direction.values()) {
                val position2 = position1.move(direction2) ?: continue
                if (board.find(position2) != Stone.NONE) {
                    continue
                }
                val position3 = position2.move(direction1) ?: continue
                if (board.find(position3) == stone &&
                    countConsecutiveStones(board, position1, stone, direction1) >= 2
                    || (countConsecutiveStones(board, position, stone, direction2) >= 2)
                ) {
                    return true
                }
            }
        }
        return false
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
            currentPos = currentPos.move(direction) ?: return count
        }
        return count
    }
}
