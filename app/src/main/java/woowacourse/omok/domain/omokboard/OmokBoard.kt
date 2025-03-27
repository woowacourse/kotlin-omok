package woowacourse.omok.domain.omokboard

import woowacourse.omok.domain.player.PlayerStone
import woowacourse.omok.domain.player.StoneColor

data class OmokBoard(
    private val _value: Map<Position, OmokBoardPointState>,
) {
    constructor(vararg stonePlace: Pair<Position, String>) : this(stonePlace.associate { it.first to it.second.toPointState() })
    constructor(width: Int = DEFAULT_OMOK_BOARD_SIZE, height: Int = DEFAULT_OMOK_BOARD_SIZE) :
        this(
            (1..width)
                .flatMap { row ->
                    (1..height).map { column ->
                        Position(RowPosition(row), ColumnPosition(column)) to OmokBoardPointState.Empty
                    }
                }.toMap(),
        )

    val width get() = _value.keys.maxOf { it.column.value }
    val height get() = _value.keys.maxOf { it.row.value }

    val value get() = _value

    fun find(position: Position): OmokBoardPointState? = _value[position]

    fun updateBoard(playerStone: PlayerStone): OmokBoard {
        val updatedBoard = _value.toMutableMap()
        updatedBoard[playerStone.position] = OmokBoardPointState.OCCUPIED(playerStone.color)
        return OmokBoard(updatedBoard)
    }

    companion object {
        private fun String.toPointState(): OmokBoardPointState {
            return when (this) {
                "Black" -> OmokBoardPointState.OCCUPIED(StoneColor.BLACK)
                "White" -> OmokBoardPointState.OCCUPIED(StoneColor.WHITE)
                else -> throw IllegalArgumentException("Unknown state $this")
            }
        }

        private const val DEFAULT_OMOK_BOARD_SIZE = 15
    }
}
