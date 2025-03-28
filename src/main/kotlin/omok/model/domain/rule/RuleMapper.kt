package omok.model.domain.rule

import omok.model.entity.board.Board
import omok.model.entity.board.BoardPositionState
import omok.model.entity.position.Position

object RuleMapper {
    fun adapt(board: Board): List<List<Int>> {
        val adapted =
            List(board.sideLength.value) { column ->
                List(board.sideLength.value) { row ->
                    val state = board.stateAt(row, column)
                    when (state) {
                        BoardPositionState.Empty -> 0
                        BoardPositionState.Exist.Black -> 1
                        BoardPositionState.Exist.White -> 2
                    }
                }
            }
        return adapted
    }

    fun adapt(position: Position): Pair<Int, Int> = Pair(position.row.value, position.column.value)
}
