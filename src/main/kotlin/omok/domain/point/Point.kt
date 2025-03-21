package omok.domain.point

import omok.domain.board.BoardStatus
import omok.domain.board.OmokColumn
import omok.domain.board.OmokRow
import omok.domain.stone.StoneColor

data class Point(
    val x: OmokColumn,
    val y: OmokRow,
    val status: BoardStatus,
) {
    companion object {
        fun of(
            value: String,
            color: StoneColor,
        ): Point {
            val col = value[0].uppercaseChar()
            val row = value.substring(1)

            return Point(
                x = OmokColumn.of(col),
                y = OmokRow.of(row),
                status = BoardStatus.Moved(color),
            )
        }
    }
}
