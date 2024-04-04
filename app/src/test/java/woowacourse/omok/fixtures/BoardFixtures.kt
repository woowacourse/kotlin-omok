package woowacourse.omok.fixtures

import woowacourse.omok.model.omok.Board
import woowacourse.omok.model.omok.OmokStones
import woowacourse.omok.model.omok.Position
import woowacourse.omok.model.omok.StoneColor

fun createBoard(vararg positions: Position = arrayOf()): Board {
    if (positions.isEmpty()) return Board(OmokStones())
    val colors = positions.indices.map { if (it % 2 == 0) StoneColor.BLACK else StoneColor.WHITE }
    val map =
        positions.zip(colors)
            .associate { (position, color) -> position to color }
    return Board(OmokStones(map))
}

fun createBlackBoard(vararg positions: Position = arrayOf()): Board {
    if (positions.isEmpty()) return Board(OmokStones())
    val map =
        positions.associateWith { StoneColor.BLACK }
    return Board(OmokStones(map))
}

fun createWhiteBoard(vararg positions: Position = arrayOf()): Board {
    if (positions.isEmpty()) return Board(OmokStones())
    val map =
        positions.associateWith { StoneColor.WHITE }
    return Board(OmokStones(map))
}
