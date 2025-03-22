package omok.domain.omokboard

@JvmInline
value class OmokBoard private constructor(
    private val _value: Map<Position, PointSate>,
) {
    val width get() = _value.keys.maxOf { it.column.value }
    val height get() = _value.keys.maxOf { it.row.value }

    val isOneEmptyLeft get() = _value.values.count { it.state == State.EMPTY } == 1

    val blackStonePoints get() =
        _value
            .filter { it.value.state == State.OCCUPIED_BLACK }
            .keys

    val whiteStonePoints get() =
        _value
            .filter { it.value.state == State.OCCUPIED_WHITE }
            .keys

    val value get() = _value.deepCopy()

    fun find(position: Position): PointSate? = _value[position]

    private fun Map<Position, PointSate>.deepCopy(): Map<Position, PointSate> = map { it.key.copy() to it.value.copy() }.toMap()

    companion object {
        fun create(
            width: Int = DEFAULT_OMOK_BOARD_SIZE,
            height: Int = DEFAULT_OMOK_BOARD_SIZE,
        ): OmokBoard =
            OmokBoard(
                (1..width)
                    .flatMap { row ->
                        (1..height).map { column ->
                            Position(RowPosition(row), ColumnPosition(column)) to PointSate()
                        }
                    }.toMap(),
            )

        private const val DEFAULT_OMOK_BOARD_SIZE = 15
    }
}
