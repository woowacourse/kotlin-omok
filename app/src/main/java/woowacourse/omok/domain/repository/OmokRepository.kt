package woowacourse.omok.domain.repository

import woowacourse.omok.domain.point.Point

interface OmokRepository {
    fun saveNewPoint(
        newPoint: Point,
        roomId: Long,
    )

    fun readAllPoint(roomId: Long): List<Point>

    fun drop()
}
