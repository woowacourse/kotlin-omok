package omok.domain.omokboard

@JvmInline
value class OmokBoard private constructor(
    val value: Map<Position, Intersection>,
) {
    val width get() = value.keys.maxOf { it.column.value }
    val height get() = value.keys.maxOf { it.row.value }

    fun find(position: Position): Intersection? = value[position]

    companion object {
        fun create(
            width: Int = DEFAULT_OMOK_BOARD_SIZE,
            height: Int = DEFAULT_OMOK_BOARD_SIZE,
        ): OmokBoard =
            OmokBoard(
                (1..width)
                    .flatMap { row ->
                        (1..height).map { column ->
                            Position(RowPosition(row), ColumnPosition(column)) to Intersection()
                        }
                    }.toMap(),
            )

        private const val DEFAULT_OMOK_BOARD_SIZE = 15
    }
}
