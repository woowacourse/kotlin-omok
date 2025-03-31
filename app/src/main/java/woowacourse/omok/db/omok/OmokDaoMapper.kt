package woowacourse.omok.db.omok

import woowacourse.omok.domain.point.Point

class OmokDaoMapper(
    private val omokDao: OmokDao,
) {
    fun saveNewPoint(
        newPoint: Point,
        roomId: Long,
    ) {
        omokDao.save(newPoint.toEntity(roomId))
    }

    fun readAllPoint(roomId: Long): List<Point> {
        return omokDao.readByRoomId(roomId).map { it.toDomain() }
    }

    fun drop() {
        omokDao.drop()
    }
}
