package woowacourse.omok.data.mapper

import omok.model.stone.position.Col
import omok.model.stone.position.Position
import omok.model.stone.position.Row
import woowacourse.omok.data.model.StoneEntity
import woowacourse.omok.model.stone.Stone
import woowacourse.omok.model.stone.StoneColor

fun StoneEntity.toDomain(): Stone =
    Stone(
        position = Position(Row(row), Col(col)),
        stoneColor = StoneColor.valueOf(color),
    )
