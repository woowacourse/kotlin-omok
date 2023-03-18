package domain.player

import domain.board.Board
import domain.library.judgment.BlackReferee
import domain.library.position.Column
import domain.library.position.Position
import domain.library.position.Row
import domain.stone.Color
import domain.stone.Point
import domain.stone.Stone

enum class PlacingPoint {
    FORBIDDEN,
    ALLOWED;

    companion object {
        private val blackReferee = BlackReferee()

        fun valueOf(currentBoard: Board, placingPoint: Point): PlacingPoint {
            if (blackReferee.isForbiddenPlacement(convertStonesToPositionsMap(currentBoard.placedStones), placingPoint.toPosition())) {
                return FORBIDDEN
            }
            return ALLOWED
        }

        // TODO
        fun Point.toPosition(): Position = Position(Column.valueOf(this.x), Row.valueOf(this.y))

        private fun convertStonesToPositionsMap(stones: List<Stone>): Map<Position, Color?> {
            val positions: MutableMap<Position, Color?> = POSITIONS.associateWith { null }.toMutableMap()
            stones.forEach { stone ->
                positions[stone.point.toPosition()] = stone.color
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
