package woowacourse.omok.model

import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.Position
import woowacourse.omok.domain.model.Position.Companion.INDEX_RANGE
import woowacourse.omok.domain.model.Stone
import woowacourse.omok.domain.model.StonePosition

fun initBoard(vararg stonePosition: StonePosition): Board {
    val board =
        INDEX_RANGE.flatMap { row ->
            INDEX_RANGE.map { col -> Position(row, col) }
        }.associateWith { Stone.NONE }
            .toMutableMap()

    board.forEach { (position, _) ->
        stonePosition.forEach {
            if (position == it.position) {
                board[position] = it.stone
            }
        }
    }
    return Board(board)
}
