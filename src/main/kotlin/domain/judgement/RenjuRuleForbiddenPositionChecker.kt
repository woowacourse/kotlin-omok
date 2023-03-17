package domain.judgement

import domain.library.BlackReferee
import domain.library.PlacementReferee
import domain.stone.Color
import domain.stone.Column
import domain.stone.Position
import domain.stone.Row
import domain.stone.Stone

class RenjuRuleForbiddenPositionChecker(private val referee: PlacementReferee = BlackReferee()) :
    ForbiddenPositionChecker {
    override fun isForbidden(placedStones: List<Stone>, newStone: Stone): Boolean {
        val placedStonesMap = convertStonesToPositionsMap(placedStones)
        if (newStone.color == Color.BLACK && referee.isForbiddenPlacement(placedStonesMap, newStone.position)) return true
        return false
    }

    private fun convertStonesToPositionsMap(stones: List<Stone>): Map<Position, Color?> {
        val positions: MutableMap<Position, Color?> = POSITIONS.associateWith { null }.toMutableMap()
        stones.forEach { stone ->
            positions[stone.position] = stone.color
        }
        return positions.toMap()
    }

    companion object {
        private val POSITIONS: List<Position> = Column.values().flatMap { column ->
            Row.values().map { row ->
                Position(column, row)
            }
        }
    }
}
