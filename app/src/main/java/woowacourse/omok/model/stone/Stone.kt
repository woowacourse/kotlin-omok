package woowacourse.omok.model.stone

import woowacourse.omok.R
import woowacourse.omok.model.GameState
import woowacourse.omok.model.position.Column
import woowacourse.omok.model.position.Row

data class Stone(
    private val row: Row,
    private val column: Column,
) {
    override fun toString(): String {
        return LAST_STONE_LOCATION_MESSAGE.format(column.comma, row.comma)
    }

    fun getRowIndex(): Int {
        return row.getIndex()
    }

    fun getColumnIndex(): Int {
        return column.getIndex()
    }

    fun getRowComma(): String {
        return row.comma
    }

    fun getBoardRowIndex(): Int {
        return row.toBoardIndex()
    }

    fun getColumnComma(): String {
        return column.comma
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
        ): GameState.LoadStone {
            return when (
                val stone =
                    stones.find { it.row.comma == row.comma && it.column.comma == column.comma }
            ) {
                null -> GameState.LoadStone.Failure(IllegalArgumentException(ERROR_NOT_STONE))
                else -> GameState.LoadStone.Success(stone)
            }
        }

        private const val ERROR_NOT_STONE = "둘 수 있는 돌이 없습니다."
        const val LAST_STONE_LOCATION_MESSAGE = "(마지막 돌의 위치: %s%s)"
        const val BLACK_STONE_NAME = "흑"
        const val WHITE_STONE_NAME = "백"
        val BLACK_STONE_RESOURCE = R.drawable.black_stone
        val WHITE_STONE_RESOURCE = R.drawable.white_stone
    }
}
