package woowacourse.omok.domain.model

import woowacourse.omok.domain.model.database.OmokTurn

data class StonePosition(val position: Position, val stone: Stone)

fun StonePosition.toOmokTurn(): OmokTurn = OmokTurn(position.row, position.col, stone.name.lowercase())
