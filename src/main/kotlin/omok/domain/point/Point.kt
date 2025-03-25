package omok.domain.point

import omok.domain.board.BoardStatus
import omok.domain.board.OmokColumn
import omok.domain.board.OmokRow
import omok.domain.stone.StoneColor
import omok.exception.ResultState
import omok.exception.execute

data class Point(
    val x: OmokColumn,
    val y: OmokRow,
    val status: BoardStatus,
) {
    companion object {
        fun of(
            value: String,
            color: StoneColor,
        ): ResultState<Point> =
            execute {
                val col = value[0].uppercaseChar()
                val row = value.substring(1)

                Point(
                    x = OmokColumn.of(col),
                    y = OmokRow.of(row),
                    status = BoardStatus.Moved(color),
                )
            }
    }
}
