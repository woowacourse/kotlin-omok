package domain

import domain.judgment.BlackReferee
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
        private const val N = 15
        private val blackReferee = BlackReferee()
        fun valueOf(stones: List<Stone>, newStone: Stone): OmokResult {
            val positions = convertStonesToPositionsMap(stones)
            if (blackReferee.isForbiddenPlacement(positions, newStone.position)) return FORBIDDEN
            return when (checkWin(stones + newStone, newStone.color)) {
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

        private fun checkWin(placedStones: List<Stone>, color: Color): Boolean {
            val dx = intArrayOf(1, 1, 0, -1)
            val dy = intArrayOf(0, 1, 1, 1)

            for (i in 0 until N) {
                for (j in 0 until N) {
                    if (placedStones.contains(Stone(Position(i, j), color)).not()) continue

                    for (k in 0 until 4) {
                        var cnt = 1
                        var nx = i + dx[k]
                        var ny = j + dy[k]

                        while (nx in 0 until N && ny in 0 until N && placedStones.contains(
                                Stone(
                                        Position(nx, ny),
                                        color
                                    )
                            )
                        ) {
                            cnt++
                            nx += dx[k]
                            ny += dy[k]

                            if (cnt >= 5) return true
                        }
                    }
                }
            }
            return false
        }

        private val POSITIONS: List<Position> = Column.values().flatMap { column ->
            Row.values().map { row ->
                Position(column, row)
            }
        }
    }
}
