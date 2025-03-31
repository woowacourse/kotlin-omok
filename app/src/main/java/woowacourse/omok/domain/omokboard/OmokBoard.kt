package woowacourse.omok.domain.omokboard

import woowacourse.omok.domain.omokboard.OmokBoardGridCell.Empty
import woowacourse.omok.domain.omokboard.OmokBoardGridCell.OCCUPIED
import woowacourse.omok.domain.player.PlayerStone
import woowacourse.omok.domain.player.StoneColor

class OmokBoard(
    val value: Map<Position, OmokBoardGridCell>,
) {
    constructor(vararg stonePlace: Pair<Position, String>) : this(stonePlace.associate { it.first to it.second.toPointState() })
    constructor(width: Int = DEFAULT_OMOK_BOARD_SIZE, height: Int = DEFAULT_OMOK_BOARD_SIZE) :
        this(
            (1..width)
                .flatMap { row ->
                    (1..height).map { column ->
                        Position(RowPosition(row), ColumnPosition(column)) to Empty
                    }
                }.toMap(),
        )

    val width = value.keys.maxOf { it.column.value }
    val height = value.keys.maxOf { it.row.value }

    fun find(position: Position): OmokBoardGridCell? = value[position]

    fun updateBoard(playerStone: PlayerStone): OmokBoard {
        val updatedBoard = value.toMutableMap()
        updatedBoard[playerStone.position] = OCCUPIED(playerStone.color)
        return OmokBoard(updatedBoard)
    }

    companion object {
        private const val DEFAULT_OMOK_BOARD_SIZE = 15

        private fun String.toPointState(): OmokBoardGridCell {
            return when (this) {
                "Black" -> OCCUPIED(StoneColor.BLACK)
                "White" -> OCCUPIED(StoneColor.WHITE)
                else -> Empty
            }
        }
    }
}
