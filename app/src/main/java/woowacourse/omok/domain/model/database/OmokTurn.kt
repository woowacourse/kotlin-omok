package woowacourse.omok.domain.model.database

import woowacourse.omok.domain.model.Position
import woowacourse.omok.domain.model.Stone
import woowacourse.omok.domain.model.StonePosition

data class OmokTurn(
    val row: Int,
    val column: Int,
    val stoneColor: String,
    val id: Long = 0L,
)

fun OmokTurn.toStonePosition(): StonePosition = StonePosition(Position(row, column), Stone.valueOf(stoneColor.uppercase()))
