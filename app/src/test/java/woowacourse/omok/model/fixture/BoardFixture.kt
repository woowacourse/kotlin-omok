package woowacourse.omok.model.fixture

import woowacourse.omok.model.Board
import woowacourse.omok.model.Color
import woowacourse.omok.model.Position

fun createPlayingBoard(vararg positions: Position): Array<Array<Color?>> {
    return Board()
        .apply { positions.forEach { place(it) } }
        .status
        .map { it.toTypedArray() }
        .toTypedArray()
}
