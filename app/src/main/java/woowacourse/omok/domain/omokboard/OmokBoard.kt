package woowacourse.omok.domain.omokboard

import woowacourse.omok.domain.player.PlayerStone

@JvmInline
value class OmokBoard private constructor(
    private val _value: MutableMap<Position, IntersectionState>,
) {
    val value: Map<Position, IntersectionState> get() = _value.toMap()
    val width: Int get() = value.keys.maxOf { it.column }
    val height: Int get() = value.keys.maxOf { it.row }

    fun find(position: Position): IntersectionState? = _value[position]

    fun update(playerStone: PlayerStone) {
        _value[playerStone.position] = playerStone.color.toIntersectionState()
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
                            Position(row, column) to IntersectionState.EMPTY
                        }
                    }.toMap()
                    .toMutableMap(),
            )

        private const val DEFAULT_OMOK_BOARD_SIZE = 15
    }
}
