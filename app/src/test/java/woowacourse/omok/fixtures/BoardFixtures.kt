package woowacourse.omok.fixtures

import woowacourse.omok.model.Board
import woowacourse.omok.model.Position
import woowacourse.omok.model.StoneColor

fun createBoard(vararg positions: Position = arrayOf()): Board {
    if (positions.isEmpty()) return Board(emptyMap())
    val colors = positions.indices.map { if (it % 2 == 0) StoneColor.BLACK else StoneColor.WHITE }
    val map =
        positions.zip(colors)
            .associate { (position, color) -> position to color }
    return Board(map)
}

fun createBlackBoard(vararg positions: Position = arrayOf()): Board {
    if (positions.isEmpty()) return Board(emptyMap())
    val map =
        positions.associateWith { StoneColor.BLACK }
    return Board(map)
}

fun createWhiteBoard(vararg positions: Position = arrayOf()): Board {
    if (positions.isEmpty()) return Board(emptyMap())
    val map =
        positions.associateWith { StoneColor.WHITE }
    return Board(map)
}
