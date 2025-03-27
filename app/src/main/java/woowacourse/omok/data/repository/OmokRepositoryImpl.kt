package woowacourse.omok.data.repository

import woowacourse.omok.data.datasource.OmokDataSource
import woowacourse.omok.data.db.toEntity
import woowacourse.omok.domain.point.Point
import woowacourse.omok.domain.repository.OmokRepository

class OmokRepositoryImpl(
    private val omokDataSource: OmokDataSource,
) : OmokRepository {
    override fun saveNewPoint(newPoint: Point) {
        omokDataSource.save(newPoint.toEntity())
    }

    override fun readAllPoint(): List<Point> {
        return omokDataSource.readAll().map { it.toDomainModel() }
    }

    override fun drop() {
        omokDataSource.drop()
    }
}
