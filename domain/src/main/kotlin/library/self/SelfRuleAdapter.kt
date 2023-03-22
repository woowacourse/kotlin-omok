package domain.library.self

import domain.CoordinateState
import domain.CoordinateState.EMPTY
import domain.CoordinateState.WHITE
import domain.Position
import domain.domain.Stones
import domain.library.Rule

class SelfRuleAdapter : Rule {
    override fun isEmpty(stones: Stones, position: Position): Boolean {
        return stones[position.y, position.x] == EMPTY
    }

    override fun isBlackWin(stones: Stones, position: Position): Boolean {
        return ExactlyFive.isExactlyFive(stones.converter(), position, WHITE)
    }

    override fun isWhiteWin(stones: Stones, position: Position): Boolean {
        val exactlyFive = ExactlyFive.isExactlyFive(stones.converter(), position, WHITE)
        val exceedFive = ExceedFive.isExceedFive(stones.converter(), position, WHITE)
        return exactlyFive or exceedFive
    }

    override fun isBlackForbidden(stones: Stones, position: Position): Boolean {
        val isThreeForbidden = ForbiddenThree.isForbiddenThree(stones.converter(), position)
        val isFourForbidden = ForbiddenFour.isForbiddenFour(stones.converter(), position)
        val exceedFive = ExceedFive.isExceedFive(stones.converter(), position, WHITE)
        return isThreeForbidden or isFourForbidden or exceedFive
    }

    private fun Stones.converter(): List<List<CoordinateState>> {
        return stones.map { it.row }
    }
}
