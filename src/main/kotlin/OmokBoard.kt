@JvmInline
value class OmokBoard(
    val board: List<Point> = CACHE_OMOK_BOARD,
) {
    companion object {
        private val OMOK_BOARD_RANGE = (1..15)
        private val CACHE_OMOK_BOARD: List<Point> =
            OMOK_BOARD_RANGE.flatMap { row ->
                OMOK_BOARD_RANGE.map { column ->
                    Point(Position(RowPosition(row), ColumnPosition(column)))
                }
            }
    }
}
