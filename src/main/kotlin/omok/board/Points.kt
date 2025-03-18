package omok.board

import omok.stone.Position
import omok.stone.StoneColor

class Points private constructor(val points: List<Point>) {
    fun placeStone(
        position: Position,
        color: StoneColor,
    ): PlaceStoneResult {
        val point = points.find { it.position == position }
        val state = point?.state ?: return PlaceStoneResult.InvalidPosition

        return when (state) {
            PointState.OPEN -> {
                point.changeColor(color)
                PlaceStoneResult.Success(point)
            }
            PointState.CLOSED -> PlaceStoneResult.Closed
            else -> PlaceStoneResult.AlreadyPlaced
        }
    }

    companion object {
        fun create(): Points {
            val points = mutableListOf<Point>()
            var row = 1
            var col = 1

            while (col <= 15) {
                val position = Position(row, col)

                points.add(Point(position))

                row++
                if (row > 15) {
                    col++
                    row = 1
                }
            }
            return Points(points)
        }
    }
}
