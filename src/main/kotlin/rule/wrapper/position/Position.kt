package rule.wrapper.position

import Col
import Row

class Position(
    val row: Row,
    val col: Col,
) {
    fun move(
        rowStep: Int,
        colStep: Int,
    ): Position = Position(row + rowStep, col + colStep)

    fun inRange(
        rowBound: Int,
        colBound: Int,
    ): Boolean = (row.value in 1..rowBound) && (col.value in 1..colBound)

    fun isSame(position: Position): Boolean = this.col.isSame(position.col) && this.row.isSame(position.row)
}
