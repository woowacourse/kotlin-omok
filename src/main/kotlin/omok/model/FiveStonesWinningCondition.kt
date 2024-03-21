package omok.model

class FiveStonesWinningCondition : WinningCondition {
    override fun isWin(board: Board, position: Position): Boolean {
        Direction.biDirections().forEach { (direction1, direction2) ->
            var count = 1
            count += continualCount(board, position, direction1)
            count += continualCount(board, position, direction2)

            if (count == WINNING_STONE_COUNT) return true
        }
        return false
    }

    private fun continualCount(
        board: Board,
        position: Position,
        direction: Direction,
    ): Int {
        val myStone = board.find(position)
        var count = 0
        var nowPos = position

        while (true) {
            nowPos = nowPos.move(direction) ?: return count
            if (board.find(nowPos) != myStone) return count
            count++
        }
    }

    companion object {
        private const val WINNING_STONE_COUNT = 5
    }
}
