package woowacourse.omok.data.db

import woowacourse.omok.data.db.OmokEntity.Companion.BLACK_STONE
import woowacourse.omok.data.db.OmokEntity.Companion.DOUBLE_FOUR_EXCEPTIONS
import woowacourse.omok.data.db.OmokEntity.Companion.DOUBLE_THREE_EXCEPTIONS
import woowacourse.omok.data.db.OmokEntity.Companion.OVER_LINE_EXCEPTIONS
import woowacourse.omok.data.db.OmokEntity.Companion.WHITE_STONE
import woowacourse.omok.domain.board.BoardStatus
import woowacourse.omok.domain.board.Column
import woowacourse.omok.domain.board.Row
import woowacourse.omok.domain.exception.RendjuExceptions
import woowacourse.omok.domain.point.Point
import woowacourse.omok.domain.stone.StoneColor

data class OmokEntity(
    val row: Int,
    val column: Int,
    val stone: String?,
) {
    fun toDomainModel(): Point {
        val stoneStatus =
            when (stone) {
                BLACK_STONE,
                WHITE_STONE,
                -> BoardStatus.Moved(StoneColor.fromString(stone))
                DOUBLE_THREE_EXCEPTIONS -> BoardStatus.Blocked(RendjuExceptions.DoubleThreeExceptions)
                DOUBLE_FOUR_EXCEPTIONS -> BoardStatus.Blocked(RendjuExceptions.DoubleFourExceptions)
                OVER_LINE_EXCEPTIONS -> BoardStatus.Blocked(RendjuExceptions.OverLineExceptions)
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
        const val DOUBLE_THREE_EXCEPTIONS = "DOUBLE_THREE_EXCEPTIONS"
        const val DOUBLE_FOUR_EXCEPTIONS = "DOUBLE_FOUR_EXCEPTIONS"
        const val OVER_LINE_EXCEPTIONS = "OVER_LINE_EXCEPTIONS"
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
                    RendjuExceptions.DoubleThreeExceptions -> DOUBLE_THREE_EXCEPTIONS
                    RendjuExceptions.DoubleFourExceptions -> DOUBLE_FOUR_EXCEPTIONS
                    RendjuExceptions.OverLineExceptions -> OVER_LINE_EXCEPTIONS
                }

            is BoardStatus.Empty -> null
        }

    return OmokEntity(
        row = this.y.value,
        column = this.x.value,
        stone = stone,
    )
}
