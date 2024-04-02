package woowacourse.omok.fixtures

import woowacourse.omok.db.GameTurnEntity

private const val NO_ID = -1L

fun gameTurnOf(
    x: Int,
    y: Int,
    id: Long = NO_ID,
    color: String = "black",
): GameTurnEntity = GameTurnEntity(id, x, y, "black")
