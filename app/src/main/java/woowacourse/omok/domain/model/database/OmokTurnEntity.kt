package woowacourse.omok.domain.model.database

import woowacourse.omok.domain.model.Position
import woowacourse.omok.domain.model.Stone
import woowacourse.omok.domain.model.StonePosition

data class OmokTurnEntity(
    val row: Int,
    val column: Int,
    val stoneColor: String,
    val id: Long = 0L,
)

fun OmokTurnEntity.toStonePosition(): StonePosition = StonePosition(Position(row, column), Stone.valueOf(stoneColor.uppercase()))
