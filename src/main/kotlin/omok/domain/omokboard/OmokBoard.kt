package omok.domain.omokboard

@JvmInline
value class OmokBoard private constructor(
    private val _value: Map<Position, Point>,
) {
    val width get() = _value.keys.maxOf { it.column.value }
    val height get() = _value.keys.maxOf { it.row.value }

    val isOneEmptyLeft get() = _value.values.count { it.state == PointState.EMPTY } == 1

    val blackStonePoints get() = _value
                            .filter { it.value.state == PointState.OCCUPIED_BLACK }
                            .keys

    val whiteStonePoints get() = _value
                            .filter { it.value.state == PointState.OCCUPIED_WHITE }
                            .keys

    val value get() = _value.deepCopy()

    fun find(position: Position): Point? = _value[position]

    private fun Map<Position, Point>.deepCopy(): Map<Position, Point> =
        map { it.key.copy() to it.value.copy() }.toMap()

    companion object {
        fun create(
            width: Int = DEFAULT_OMOK_BOARD_SIZE,
            height: Int = DEFAULT_OMOK_BOARD_SIZE,
        ): OmokBoard =
            OmokBoard(
                (1..width)
                    .flatMap { row ->
                        (1..height).map { column ->
                            Position(RowPosition(row), ColumnPosition(column)) to Point()
                        }
                    }.toMap(),
            )

        private const val DEFAULT_OMOK_BOARD_SIZE = 15
    }
}
