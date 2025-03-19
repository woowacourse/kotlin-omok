package omok.model.domain.omokboard

@JvmInline
value class OmokBoard private constructor(
    val value: Map<Position, Point>,
) {
    fun find(position: Position): Point? = value[position]

    companion object {
        fun create(): OmokBoard =
            OmokBoard(
                OMOK_BOARD_RANGE
                    .flatMap { row ->
                        OMOK_BOARD_RANGE.map { column ->
                            Position(RowPosition(row), ColumnPosition(column)) to Point()
                        }
                    }.toMap(),
            )

        private val OMOK_BOARD_RANGE = (1..15)
    }
}
