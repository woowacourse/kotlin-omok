package woowacourse.omok.domain.omokboard

import woowacourse.omok.domain.player.PlayerStone

@JvmInline
value class OmokBoard private constructor(
    private val _value: Map<Position, Intersection>,
) {
    val value: Map<Position, Intersection> get() = _value.toMap()
    val width: Int get() = value.keys.maxOf { it.column.value }
    val height: Int get() = value.keys.maxOf { it.row.value }

    fun find(position: Position): Intersection? = _value[position]

    fun update(playerStone: PlayerStone) {
        _value[playerStone.position]?.updateState(playerStone.color)
    }

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
