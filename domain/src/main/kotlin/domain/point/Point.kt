package domain.point

import domain.rule.Col
import domain.rule.Row

typealias PointDomain = Point

data class Point(val row: Row, val col: Col) {
    fun move(rowStep: Int, colStep: Int): Point = copy(row = row + rowStep, col = col + colStep)

    fun inRange(rowBound: Row, colBound: Col): Boolean =
        (row in MIN_BOUND..rowBound) && (col in MIN_BOUND..colBound)

    companion object {
        private const val MIN_BOUND = 1
    }
}
