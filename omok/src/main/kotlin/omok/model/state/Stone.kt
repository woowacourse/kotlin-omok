package omok.model.state

import omok.model.Column
import omok.model.Row
import omok.model.turn.BlackTurn
import omok.model.turn.FinishedTurn
import omok.model.turn.FinishedTurn.Companion.ERROR_FINISHED_MESSAGE
import omok.model.turn.Turn
import omok.model.turn.WhiteTurn

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

        fun getStoneIcon(turn: Turn): Char {
            return when (turn) {
                is BlackTurn -> '●'
                is WhiteTurn -> '○'
                is FinishedTurn -> throw java.lang.IllegalArgumentException(ERROR_FINISHED_MESSAGE)
            }
        }

        fun getStoneName(turn: Turn): String {
            return when (turn) {
                is BlackTurn -> "흑"
                is WhiteTurn -> "백"
                is FinishedTurn -> throw java.lang.IllegalArgumentException(ERROR_FINISHED_MESSAGE)
            }
        }
    }
}
