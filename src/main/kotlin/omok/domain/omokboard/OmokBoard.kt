package omok.domain.omokboard

import omok.domain.player.PlayerStone

data class OmokBoard(
    private val _value: Map<Position, PointState>,
) {
    constructor(vararg stonePlace: Pair<String, String>) : this(stonePlace.associate { Position(it.first) to it.second.toPointState() })

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

    fun find(position: Position): PointState? = _value[position]

    fun updateBoard(playerStone: PlayerStone) {
        this.find(playerStone.position)
            ?.updateState(playerStone.color)
    }

    private fun Map<Position, PointState>.deepCopy(): Map<Position, PointState> = map { it.key.copy() to it.value }.toMap()

    companion object {
        fun create(
            width: Int = DEFAULT_OMOK_BOARD_SIZE,
            height: Int = DEFAULT_OMOK_BOARD_SIZE,
        ): OmokBoard =
            OmokBoard(
                (1..width)
                    .flatMap { row ->
                        (1..height).map { column ->
                            Position(RowPosition(row), ColumnPosition(column)) to PointState()
                        }
                    }.toMap(),
            )

        private fun String.toPointState(): PointState {
            return when (this) {
                "Black" -> PointState(State.OCCUPIED_BLACK)
                "White" -> PointState(State.OCCUPIED_WHITE)
                else -> throw IllegalArgumentException("Unknown state $this")
            }
        }

        private const val DEFAULT_OMOK_BOARD_SIZE = 15
    }
}
