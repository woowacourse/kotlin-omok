package omok.domain.omokboard

import omok.domain.player.PlayerStone
import omok.domain.player.StoneColor

data class OmokBoard(
    private val _value: Map<Position, PointState>,
) {
    constructor(vararg stonePlace: Pair<String, String>) : this(stonePlace.associate { Position(it.first) to it.second.toPointState() })

    val width get() = _value.keys.maxOf { it.column.value }
    val height get() = _value.keys.maxOf { it.row.value }

    val value get() = _value

    fun find(position: Position): PointState? = _value[position]

    fun updateBoard(playerStone: PlayerStone): OmokBoard {
        val updatedBoard = _value.toMutableMap()
        updatedBoard[playerStone.position] = PointState.OCCUPIED(playerStone.color)
        return OmokBoard(updatedBoard)
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
                            Position(RowPosition(row), ColumnPosition(column)) to PointState.Empty
                        }
                    }.toMap(),
            )

        private fun String.toPointState(): PointState {
            return when (this) {
                "Black" -> PointState.OCCUPIED(StoneColor.BLACK)
                "White" -> PointState.OCCUPIED(StoneColor.WHITE)
                else -> throw IllegalArgumentException("Unknown state $this")
            }
        }

        private const val DEFAULT_OMOK_BOARD_SIZE = 15
    }
}
