package domain.library.adapter

import domain.board.Board
import domain.library.placing.BlackReferee
import domain.library.position.Column
import domain.library.position.Position
import domain.library.position.Row
import domain.stone.Color
import domain.stone.Point
import domain.stone.Stone

class BlackRefreeAdapter(
    private val BlackReferee: BlackReferee = BlackReferee()
) {

    fun isForbiddenPlacement(currentBoard: Board, placingPoint: Point): Boolean {

        return BlackReferee.isForbiddenPlacement(
            board = convertStonesToPositionsMap(currentBoard.placedStones),
            position = placingPoint.toPosition()
        )
    }

    private fun convertStonesToPositionsMap(stones: List<Stone>): Map<Position, Color?> {
        val positions: MutableMap<Position, Color?> = positions.associateWith { null }.toMutableMap()
        stones.forEach { stone ->
            positions[stone.point.toPosition()] = stone.color
        }

        return positions.toMap()
    }

    private fun Point.toPosition(): Position {

        return Position(Column.valueOf(x), Row.valueOf(y))
    }

    private val positions: List<Position> = Column.values().flatMap { column ->
        Row.values().map { row ->
            Position(column, row)
        }
    }
}
