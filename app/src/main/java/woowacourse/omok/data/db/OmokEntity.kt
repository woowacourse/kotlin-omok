package woowacourse.omok.data.db

import woowacourse.omok.data.db.OmokEntity.Companion.BLACK_STONE
import woowacourse.omok.data.db.OmokEntity.Companion.DOUBLE_FOUR_STONE
import woowacourse.omok.data.db.OmokEntity.Companion.DOUBLE_THREE_STONE
import woowacourse.omok.data.db.OmokEntity.Companion.OVER_LINE_STONE
import woowacourse.omok.data.db.OmokEntity.Companion.WHITE_STONE
import woowacourse.omok.domain.board.BoardStatus
import woowacourse.omok.domain.board.Column
import woowacourse.omok.domain.board.Row
import woowacourse.omok.domain.exception.RendjuException
import woowacourse.omok.domain.point.Point
import woowacourse.omok.domain.stone.StoneColor

data class OmokEntity(
    val row: Int,
    val column: Int,
    val stone: String,
) {
    fun toDomainModel(): Point {
        val stoneStatus =
            when (stone) {
                BLACK_STONE,
                WHITE_STONE,
                -> BoardStatus.Moved(StoneColor.fromString(stone))
                DOUBLE_THREE_STONE -> BoardStatus.Blocked(RendjuException.DoubleThreeException)
                DOUBLE_FOUR_STONE -> BoardStatus.Blocked(RendjuException.DoubleFourException)
                OVER_LINE_STONE -> BoardStatus.Blocked(RendjuException.OverLineException)
                else -> BoardStatus.Empty
            }

        return Point(
            x = Column(column),
            y = Row(row),
            status = stoneStatus,
        )
    }

    companion object {
        const val BLACK_STONE = "BLACK"
        const val WHITE_STONE = "WHITE"
        const val DOUBLE_THREE_STONE = "DOUBLE_THREE"
        const val DOUBLE_FOUR_STONE = "DOUBLE_FOUR"
        const val OVER_LINE_STONE = "OVER_LINE"
    }
}

fun Point.toEntity(): OmokEntity {
    val stone =
        when (val status = this.status) {
            is BoardStatus.Moved ->
                when (status.color) {
                    StoneColor.BLACK -> BLACK_STONE
                    StoneColor.WHITE -> WHITE_STONE
                }

            is BoardStatus.Blocked ->
                when (status.cause) {
                    RendjuException.DoubleThreeException -> DOUBLE_THREE_STONE
                    RendjuException.DoubleFourException -> DOUBLE_FOUR_STONE
                    RendjuException.OverLineException -> OVER_LINE_STONE
                }
            else -> throw IllegalArgumentException()
        }

    return OmokEntity(
        row = this.y.value,
        column = this.x.value,
        stone = stone,
    )
}
