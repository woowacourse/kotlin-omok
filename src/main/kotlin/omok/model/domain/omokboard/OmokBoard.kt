package omok.model.domain.omokboard

@JvmInline
value class OmokBoard(
    val value: List<Point>,
) {
    companion object {
        fun create(): OmokBoard =
            OmokBoard(
                OMOK_BOARD_RANGE.flatMap { row ->
                    OMOK_BOARD_RANGE.map { column ->
                        Point(Position(RowPosition(row), ColumnPosition(column)))
                    }
                },
            )

        private val OMOK_BOARD_RANGE = (1..15)
    }
}
