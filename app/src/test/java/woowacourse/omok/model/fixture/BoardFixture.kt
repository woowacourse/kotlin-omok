package woowacourse.omok.model.fixture

import woowacourse.omok.model.Board
import woowacourse.omok.model.Position
import woowacourse.omok.model.Row
import woowacourse.omok.model.Rows

fun createPlayingBoard(vararg positions: Position): Rows {
    return Rows(
        Board()
            .apply { positions.forEach { place(it) } }
            .boardPlacement
            .values
            .map { Row(it.placementData) },
    )
}
