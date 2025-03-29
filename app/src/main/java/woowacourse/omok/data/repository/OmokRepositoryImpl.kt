package woowacourse.omok.data.repository

import woowacourse.omok.data.dao.OmokDao
import woowacourse.omok.data.db.omok.toEntity
import woowacourse.omok.domain.point.Point
import woowacourse.omok.domain.repository.OmokRepository

class OmokRepositoryImpl(
    private val omokDao: OmokDao,
) : OmokRepository {
    override fun saveNewPoint(newPoint: Point) {
        omokDao.save(newPoint.toEntity())
    }

    override fun readAllPoint(): List<Point> {
        return omokDao.readAll().map { it.toDomain() }
    }

    override fun drop() {
        omokDao.drop()
    }
}
