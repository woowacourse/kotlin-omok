package omock.model.stone

import omock.model.player.BlackPlayer
import omock.model.player.Player
import omock.model.player.WhitePlayer
import omock.model.position.Column
import omock.model.position.Row

data class Stone(
    val row: Row,
    val column: Column,
) {
    override fun toString(): String {
        return LAST_STONE_LOCATION_MESSAGE.format(column.comma, row.comma)
    }

    companion object {
        val stones: List<Stone> = generateStones()

        private fun generateStones(): List<Stone> {
            return Row.ROW_RANGE.flatMap { rowComma ->
                (Column.COLUM_RANGE).map { columnComma ->
                    Stone(
                        row = Row(rowComma),
                        column = Column(columnComma),
                    )
                }
            }
        }

        fun from(
            row: Row,
            column: Column,
        ): Stone {
            return stones.find {
                it.row.comma == row.comma && it.column.comma == column.comma
            } ?: throw IllegalArgumentException()
        }

        const val LAST_STONE_LOCATION_MESSAGE = "(마지막 돌의 위치: %s%s)"
    }
}
