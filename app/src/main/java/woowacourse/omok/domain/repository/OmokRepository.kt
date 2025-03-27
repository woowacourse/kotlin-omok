package woowacourse.omok.domain.repository

import woowacourse.omok.domain.point.Point

interface OmokRepository {
    fun saveNewPoint(newPoint: Point)

    fun readAllPoint(): List<Point>

    fun drop()
}
