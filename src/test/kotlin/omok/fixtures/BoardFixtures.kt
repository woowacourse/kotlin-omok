package omok.fixtures

import omok.model.Board
import omok.model.OmokStone
import omok.model.Position
import omok.model.StoneColor

fun createBoard(vararg positions: Position = arrayOf()): Board {
    if (positions.isEmpty()) return Board(emptyMap())
    val colors = positions.indices.map { if (it % 2 == 0) StoneColor.BLACK else StoneColor.WHITE }
    val map =
        positions.zip(colors)
            .associate { (point, color) -> point to OmokStone(point, color) }
    return Board(map)
}

fun createBlackBoard(vararg positions: Position = arrayOf()): Board {
    if (positions.isEmpty()) return Board(emptyMap())
    val colors = positions.indices.map { StoneColor.BLACK }
    val map =
        positions.zip(colors)
            .associate { (point, color) -> point to OmokStone(point, color) }
    return Board(map)
}

fun createWhiteBoard(vararg positions: Position = arrayOf()): Board {
    if (positions.isEmpty()) return Board(emptyMap())
    val colors = positions.indices.map { StoneColor.WHITE }
    val map =
        positions.zip(colors)
            .associate { (point, color) -> point to OmokStone(point, color) }
    return Board(map)
}
