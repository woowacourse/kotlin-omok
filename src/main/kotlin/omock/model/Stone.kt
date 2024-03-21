package omock.model

data class Stone(
    val row: Row,
    val column: Column,
) {
    companion object {
        val stones = Row.ROW_RANGE.flatMap { rowComma ->
            (Column.COLUM_RANGE).map { columnComma ->
                Stone(
                    row = Row(rowComma),
                    column = Column(columnComma),
                )
            }
        }

        fun from(row: Row, column: Column): Stone {
            return stones.find {
                it.row.comma == row.comma && it.column.comma == column.comma
            } ?: throw IllegalArgumentException()
        }

        fun getStoneIcon(player: Player): Char {
            return when (player) {
                is BlackPlayer -> '●'
                is WhitePlayer -> '○'
            }
        }

        fun getStoneName(player: Player): String {
            return when (player) {
                is BlackPlayer -> "흑"
                is WhitePlayer -> "백"
            }
        }
    }
}
