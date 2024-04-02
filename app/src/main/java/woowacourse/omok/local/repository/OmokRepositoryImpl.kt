package woowacourse.omok.local.repository

import woowacourse.omok.local.db.OmokDao
import woowacourse.omok.local.mapper.toLocal
import woowacourse.omok.local.mapper.toPresentation
import woowacourse.omok.presentation.model.Omok

class OmokRepositoryImpl(
    private val omokDao: OmokDao,
) : OmokRepository {
    override fun insertOmok(omok: Omok): Result<Unit> =
        runCatching {
            omokDao.insertOmok(omok.toLocal())
        }

    override fun selectOmok(): Result<List<Omok>> =
        runCatching {
            omokDao.selectOmok().map { omokEntity ->
                omokEntity.toPresentation()
            }
        }

    override fun deleteOmok(omok: Omok): Result<Unit> =
        runCatching {
            omokDao.deleteOmok(omok.toLocal())
        }

    override fun deleteAllOmok(): Result<Unit> =
        runCatching {
            omokDao.deleteAllOmok()
        }
}
