package omok.model.rule.winning

import omok.model.Board
import omok.model.Position

class ContinualStonesWinningCondition(private val continualStones: ContinualStones) : WinningCondition {
    override fun isWin(
        board: Board,
        position: Position,
    ): Boolean {
        if (continualStones.count(board, position) == WINNING_STONE_COUNT) return true
        return false
    }

    companion object {
        private const val WINNING_STONE_COUNT = 5
    }
}
