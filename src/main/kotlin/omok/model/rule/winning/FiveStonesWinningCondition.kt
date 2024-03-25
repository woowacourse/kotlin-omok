package omok.model.rule.winning

import omok.model.Board
import omok.model.Direction
import omok.model.Position

class FiveStonesWinningCondition : WinningCondition {
    override fun isWin(
        board: Board,
        position: Position,
    ): Boolean {
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
        val stone = board.findOrNull(position)
        var count = 0
        var nowPosition = position

        while (true) {
            nowPosition = nowPosition.move(direction)
            if (board.findOrNull(nowPosition) != stone) return count
            count++
        }
    }

    companion object {
        private const val WINNING_STONE_COUNT = 5
    }
}
