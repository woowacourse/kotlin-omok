package woowacourse.omok.domain.model

import woowacourse.omok.database.omokturn.OmokTurnEntity

data class StonePosition(val position: Position, val stone: Stone)

fun StonePosition.toOmokTurn(): OmokTurnEntity = OmokTurnEntity(position.row, position.col, stone.name.lowercase())
