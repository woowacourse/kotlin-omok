package domain.judgement

import domain.stone.Color
import domain.stone.Position
import domain.stone.Stone

class RenjuRule(
    private val winningCondition: WinningCondition = FiveStoneWinningCondition(),
    private val forbiddenCondition: ForbiddenCondition = RenjuRuleForbiddenCondition()
) : Rule {
    override fun isForbidden(board: Map<Position, Color?>, newStone: Stone): Boolean {
        return forbiddenCondition.isForbidden(board, newStone)
    }

    override fun isWin(board: Map<Position, Color?>, newStone: Stone): Boolean {
        return winningCondition.isWin(board, newStone)
    }
}
