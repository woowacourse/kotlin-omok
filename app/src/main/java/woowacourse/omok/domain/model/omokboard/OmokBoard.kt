package woowacourse.omok.domain.model.omokboard

import woowacourse.omok.domain.model.player.PlayerStone

@JvmInline
value class OmokBoard(
    private val value: MutableMap<Position, PointState>,
) {
    val snapshot: Map<Position, PointState> get() = value.toMap()
    val width: Int get() = snapshot.keys.maxOf { it.column }
    val height: Int get() = snapshot.keys.maxOf { it.row }

    fun find(position: Position): PointState? = value[position]

    fun update(playerStone: PlayerStone) {
        value[playerStone.position] = playerStone.color.toIntersectionState()
    }

    fun clear() {
        value.entries.forEach { it.setValue(PointState.EMPTY) }
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
                            Position(row, column) to PointState.EMPTY
                        }
                    }.toMap()
                    .toMutableMap(),
            )

        private const val DEFAULT_OMOK_BOARD_SIZE = 15
    }
}
