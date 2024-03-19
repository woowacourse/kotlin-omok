package omok.fixtures

import omok.model.Point
import omok.model.StoneColor
import omok.model.Board
import omok.model.OmokStone

fun createBoard(vararg points: Point = arrayOf()): Board {
    val colors = points.indices.map { if (it % 2 == 0) StoneColor.BLACK else StoneColor.WHITE }
    val map =
        points.zip(colors)
            .associate { (point, color) -> point to OmokStone(point, color) }
    return Board(map)
}
