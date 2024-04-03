package woowacourse.omok.db

import woowacourse.omok.model.Color
import woowacourse.omok.model.Column
import woowacourse.omok.model.Coordinate
import woowacourse.omok.model.Row
import woowacourse.omok.model.Stone

object StoneMapper {
    fun toStone(stoneEntity: StoneEntity): Stone {
        return Stone(
            Color.valueOf(stoneEntity.color.uppercase()),
            Coordinate(Row(stoneEntity.row), Column(stoneEntity.column)),
        )
    }
}
