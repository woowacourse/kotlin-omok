package woowacourse.omok.data.local

import android.content.Context
import woowacourse.omok.data.local.dao.OmokDao
import woowacourse.omok.data.local.entity.OmokEntity

class SQLiteDataSource(context: Context) : LocalDataSource {
    private val omokDao: OmokDao = OmokDao(context)

    override fun save(omokEntity: OmokEntity): OmokEntity {
        return omokDao.save(omokEntity)
    }

    override fun findAll(): List<OmokEntity> {
        return omokDao.findAll()
    }

    override fun drop() {
        return omokDao.drop()
    }
}
