package woowacourse.omok.data

import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.position.Column
import woowacourse.omok.domain.model.position.Position
import woowacourse.omok.domain.model.position.Row
import woowacourse.omok.domain.model.stone.Stone
import woowacourse.omok.domain.model.stone.StoneType

fun StoneEntity.toStone(board: Board) =
    Stone(
        Position(
            Column.from(y, board.column),
            Row.from(x, board.row),
        ),
        StoneType.valueOf(stoneType),
    )

fun Stone.toStoneEntity() =
    StoneEntity(
        position.column.value,
        position.row.value,
        stoneType.name,
    )
