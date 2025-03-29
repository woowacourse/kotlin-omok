package woowacourse.omok.domain.omokboard

import woowacourse.omok.domain.player.PlayerStone
import woowacourse.omok.domain.player.StoneColor

data class OmokBoard(
    val value: Map<Position, OmokBoardGridCell>,
) {
    constructor(vararg stonePlace: Pair<Position, String>) : this(stonePlace.associate { it.first to it.second.toPointState() })
    constructor(width: Int = DEFAULT_OMOK_BOARD_SIZE, height: Int = DEFAULT_OMOK_BOARD_SIZE) :
        this(
            (1..width)
                .flatMap { row ->
                    (1..height).map { column ->
                        Position(RowPosition(row), ColumnPosition(column)) to OmokBoardGridCell.Empty
                    }
                }.toMap(),
        )

    val width get() = value.keys.maxOf { it.column.value }
    val height get() = value.keys.maxOf { it.row.value }

    fun find(position: Position): OmokBoardGridCell? = value[position]

    fun updateBoard(playerStone: PlayerStone): OmokBoard {
        val updatedBoard = value.toMutableMap()
        updatedBoard[playerStone.position] = OmokBoardGridCell.OCCUPIED(playerStone.color)
        return OmokBoard(updatedBoard)
    }

    companion object {
        private fun String.toPointState(): OmokBoardGridCell {
            return when (this) {
                "Black" -> OmokBoardGridCell.OCCUPIED(StoneColor.BLACK)
                "White" -> OmokBoardGridCell.OCCUPIED(StoneColor.WHITE)
                else -> throw IllegalArgumentException("Unknown state $this")
            }
        }

        private const val DEFAULT_OMOK_BOARD_SIZE = 15
    }
}
