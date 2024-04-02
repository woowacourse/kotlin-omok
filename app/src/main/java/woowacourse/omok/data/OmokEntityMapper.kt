package woowacourse.omok.data

import omock.model.Position
import omock.model.board.Block
import omock.model.board.BlockState
import woowacourse.omok.db.GameTurnEntity

private const val NO_ID = -1L

fun GameTurnEntity.toOmokStone(): Block {
    val (id, x, y, color) = this
    return Block(Position(x, y), BlockState.valueOf(color))
}

fun Block.toGameTurnEntity(): GameTurnEntity {
    val (position, color) = this
    val (x, y) = position
    return GameTurnEntity(NO_ID, x, y, color.name)
}
