package domain.library.ark

import domain.CoordinateState
import domain.CoordinateState.BLACK
import domain.CoordinateState.EMPTY
import domain.CoordinateState.WHITE
import domain.Position
import domain.domain.Row
import domain.domain.Stones
import domain.library.Rule

class ArkRuleAdapter : Rule {
    override fun isEmpty(stones: Stones, position: Position): Boolean {
        return stones[position.y, position.x] == EMPTY
    }

    override fun isBlackWin(stones: Stones, position: Position): Boolean {
        return BlackWinRule.validate(stones.converter(), position.converter())
    }

    override fun isWhiteWin(stones: Stones, position: Position): Boolean {
        return WhiteWinRule.validate(stones.converter(), position.converter())
    }

    override fun isBlackForbidden(stones: Stones, position: Position): Boolean {
        val isThreeForbidden = ThreeThreeRule.validate(stones.converter(), position.converter())
        val isFourForbidden = FourFourRule.validate(stones.converter(), position.converter())
        val isOverLined = OverLineRule.validate(stones.converter(), position.converter())
        return isThreeForbidden or isFourForbidden or isOverLined
    }

    private fun Stones.converter(): List<List<Int>> = stones.map { row -> row.converter() }

    private fun Row.converter(): List<Int> = row.map { coordState -> coordState.converter() }

    private fun CoordinateState.converter(): Int {
        return when (this) {
            BLACK -> 1
            WHITE -> 2
            EMPTY -> 0
        }
    }

    private fun Position.converter(): Pair<Int, Int> = Pair(x, y)
}
