package domain

import domain.judgment.BlackReferee
import domain.judgment.WinningReferee
import domain.stone.Color
import domain.stone.Column
import domain.stone.Position
import domain.stone.Row
import domain.stone.Stone

enum class OmokResult {
    RUNNING,
    FIVE_STONE_WINNING,
    FORBIDDEN;
    // THREE_TO_THREE,
    // FOUR_TO_FOUR,
    // LONG_STONES;

    companion object {

        private val blackReferee = BlackReferee()
        private val winningReferee = WinningReferee()
        fun valueOf(stones: List<Stone>, newStone: Stone): OmokResult {
            val positions = convertStonesToPositionsMap(stones)
            if (blackReferee.isForbiddenPlacement(positions, newStone.position)) return FORBIDDEN
            return when (winningReferee.hasFiveOrMoreStoneInRow(stones + newStone, newStone.color)) {
                true -> FIVE_STONE_WINNING
                false -> RUNNING
            }
        }

        private fun convertStonesToPositionsMap(stones: List<Stone>): Map<Position, Color?> {
            val positions: MutableMap<Position, Color?> = POSITIONS.associateWith { null }.toMutableMap()
            stones.forEach { stone ->
                positions[stone.position] = stone.color
            }
            return positions.toMap()
        }

        private val POSITIONS: List<Position> = Column.values().flatMap { column ->
            Row.values().map { row ->
                Position(column, row)
            }
        }
    }
}
