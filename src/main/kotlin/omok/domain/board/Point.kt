package omok.domain.board

data class Point(
    val x: OmokColumn,
    val y: OmokRow,
    val stoneStatus: StoneStatus,
) {
    companion object {
        fun of(
            row: String,
            column: Char,
            stoneStatus: StoneStatus,
        ): Point {
            val x = OmokColumn.of(column)
            val y = OmokRow.of(row)
            return Point(x, y, stoneStatus)
        }
    }
}
