package omok.model.fixture

import omok.model.Board
import omok.model.Color
import omok.model.Position

fun createPlayingBoard(vararg positions: Position): Array<Array<Color?>> {
    return Board()
        .apply { positions.forEach { place(it) } }
        .status
        .map { it.toTypedArray() }
        .toTypedArray()
}
