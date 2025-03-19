package omok.model.domain.omokboard

@JvmInline
value class OmokBoard private constructor(
    val value: Map<Position, Point>,
) {
    fun find(position: Position): Point? = value[position]

    companion object {
        fun create(
            width: Int = OMOK_BOARD_SIZE,
            height: Int = OMOK_BOARD_SIZE,
        ): OmokBoard =
            OmokBoard(
                (1..width)
                    .flatMap { row ->
                        (1..height).map { column ->
                            Position(RowPosition(row), ColumnPosition(column)) to Point()
                        }
                    }.toMap(),
            )

        private const val OMOK_BOARD_SIZE = 15
    }
}
