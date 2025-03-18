package omok.board

import omok.stone.Position
import omok.stone.StoneColor
import java.lang.IllegalArgumentException

class Points private constructor(val points: List<Point>) {
    fun placeStone(
        position: Position,
        color: StoneColor,
    ) {
        val point = points.find { it.position == position }
        if (point?.state == PointState.OPEN) {
            point.changeColor(color)
            return
        }

        throw IllegalArgumentException("둘 수 없는 칸입니다")
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
