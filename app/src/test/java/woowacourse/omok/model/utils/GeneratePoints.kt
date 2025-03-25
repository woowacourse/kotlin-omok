package woowacourse.omok.model.utils

import woowacourse.omok.model.board.BoardSize
import woowacourse.omok.model.board.Point
import woowacourse.omok.model.board.StoneColor

fun generatePoints(
    points: Map<String, StoneColor?>,
    size: Int = 15,
): Map<Point, StoneColor?> {
    val newPoints =
        points
            .map { (rawPoint, color) ->
                val point = rawPoint.toPosition()
                Point(point.first, point.second) to color
            }.toMap()
            .toMutableMap()

    for (row in BoardSize.MIN_SIZE..size) {
        for (col in BoardSize.MIN_SIZE..size) {
            val key = Point(row, col)
            newPoints[key] = newPoints.getOrDefault(key, null)
        }
    }

    return newPoints
}

fun Char.alphabetToInt(): Int = this.uppercaseChar().let { if (it in 'A'..'Z') it - 'A' + 1 else -1 }

fun String.toPosition(): Pair<Int, Int> = this[0].alphabetToInt() to this.substring(1).toInt()

fun String.toPoint(): Point = this.toPosition().run { Point(first, second) }
