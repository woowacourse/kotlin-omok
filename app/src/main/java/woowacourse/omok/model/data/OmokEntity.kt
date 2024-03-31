package woowacourse.omok.model.data

import woowacourse.omok.model.board.Position
import woowacourse.omok.model.board.Stone

data class OmokEntity(
    val row: Int,
    val col: Int,
    val stone: String,
    val id: Long = 0L,
)

fun OmokEntity(
    position: Position,
    stone: Stone,
): OmokEntity {
    val entityStone = if (stone == Stone.BLACK) BLACK_STONE else WHITE_STONE
    return OmokEntity(position.row, position.col, entityStone)
}

private const val BLACK_STONE = "black"
private const val WHITE_STONE = "white"
