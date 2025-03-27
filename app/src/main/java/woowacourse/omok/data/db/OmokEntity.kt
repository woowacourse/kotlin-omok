package woowacourse.omok.data.db

import woowacourse.omok.domain.board.BoardStatus
import woowacourse.omok.domain.board.Column
import woowacourse.omok.domain.board.Row
import woowacourse.omok.domain.point.Point
import woowacourse.omok.domain.stone.StoneColor

data class OmokEntity(
    val id: Long = 0L,
    val row: Int,
    val column: Int,
    val stone: String?,
) {
    fun toDomainModel(): Point {
        val color = StoneColor.fromString(stone)

        return Point(
            x = Column(column),
            y = Row(row),
            status = BoardStatus.Moved(color),
        )
    }
}

fun Point.toEntity(): OmokEntity {
    val stone =
        when (val status = this.status) {
            is BoardStatus.Moved -> status.color.name
            else -> null
        }

    return OmokEntity(
        row = this.y.value,
        column = this.x.value,
        stone = stone,
    )
}
