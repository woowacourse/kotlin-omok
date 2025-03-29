package woowacourse.omok.data.repository

import woowacourse.omok.data.dao.OmokDao
import woowacourse.omok.data.db.omok.toEntity
import woowacourse.omok.domain.point.Point
import woowacourse.omok.domain.repository.OmokRepository

class OmokRepositoryImpl(
    private val omokDao: OmokDao,
) : OmokRepository {
    override fun saveNewPoint(
        newPoint: Point,
        roomId: Long,
    ) {
        omokDao.save(newPoint.toEntity(roomId))
    }

    override fun readAllPoint(roomId: Long): List<Point> {
        return omokDao.readByRoomId(roomId).map { it.toDomain() }
    }

    override fun drop() {
        omokDao.drop()
    }
}
