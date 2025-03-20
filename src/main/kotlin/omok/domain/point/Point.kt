package omok.domain.point

import omok.domain.board.OmokColumn
import omok.domain.board.OmokRow
import omok.domain.board.StoneStatus

data class Point(
    val x: OmokColumn,
    val y: OmokRow,
    val stoneStatus: StoneStatus,
) {
    companion object {
        fun of(
            value: String,
            stoneStatus: StoneStatus,
        ): Point {
            val col = value[0].uppercaseChar()
            val row = value.substring(1)

            val x = OmokColumn.of(col)
            val y = OmokRow.of(row)
            return Point(x, y, stoneStatus)
        }
    }
}
