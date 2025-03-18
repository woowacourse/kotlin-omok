@JvmInline
value class OmokBoard(
    val board: List<Point> = CACHE_OMOK_BOARD,
) {
    companion object {
        private val OMOK_BOARD_SIZE = (1..15)
        private val CACHE_OMOK_BOARD: List<Point> =
            OMOK_BOARD_SIZE.flatMap { row ->
                OMOK_BOARD_SIZE.map { column ->
                    Point(Position(RowPosition(row), ColumnPosition(column)))
                }
            }
    }
}
