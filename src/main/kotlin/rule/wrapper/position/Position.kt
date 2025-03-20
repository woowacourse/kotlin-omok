package rule.wrapper.position

import Col
import Row
import rule.wrapper.direction.Direction

/**
 * TODO: 원래 data class 였는데 equals ,hashcode 오버라이딩 느낌으로 사용해야되다보니까 클래스로 변경하고 isSame 메서드 생성
 */

class Position(val row: Row, val col: Col) {
    fun move(
        rowStep: Int,
        colStep: Int,
    ): Position = Position(row + rowStep, col + colStep)

    // TODO : 나중에 매개변수 Row, Col 타입으로 변환 필요
    fun inRange(
        rowBound: Int,
        colBound: Int,
    ): Boolean = (row.value in 1..rowBound) && (col.value in 1..colBound)

    fun isSame(position: Position): Boolean {
        return this.col.isSame(position.col) && this.row.isSame(position.row)
    }

    fun isEdgePosition(direction: Direction): Boolean =
        when {
            direction.isGoLeft() && this.col.isMin() -> true
            direction.isGoRight() && this.col.isMax() -> true
            direction.isGoUp() && this.row.isMax() -> true
            direction.isGoDown() && this.row.isMin() -> true
            else -> false
        }
}
