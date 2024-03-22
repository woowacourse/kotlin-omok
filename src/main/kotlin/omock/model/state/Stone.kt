package omock.model.state

import omock.model.Column
import omock.model.Row
import omock.model.turn.BlackTurn
import omock.model.turn.FinishedTurn
import omock.model.turn.FinishedTurn.Companion.ERROR_FINISHED_MESSAGE
import omock.model.turn.Turn
import omock.model.turn.WhiteTurn

data class Stone(
    val row: Row,
    val column: Column,
) {
    companion object {
        val stones =
            Row.ROW_RANGE.flatMap { rowComma ->
                (Column.COLUM_RANGE).map { columnComma ->
                    Stone(
                        row = Row(rowComma),
                        column = Column(columnComma),
                    )
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

        fun getStoneIcon(player: Turn): Char {
            return when (player) {
                is BlackTurn -> '●'
                is WhiteTurn -> '○'
                is FinishedTurn -> throw java.lang.IllegalArgumentException(ERROR_FINISHED_MESSAGE)
            }
        }

        fun getStoneName(player: Turn): String {
            return when (player) {
                is BlackTurn -> "흑"
                is WhiteTurn -> "백"
                is FinishedTurn -> throw java.lang.IllegalArgumentException(ERROR_FINISHED_MESSAGE)
            }
        }
    }
}
