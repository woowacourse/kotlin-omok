package woowacourse.omok.data.local

import woowacourse.omok.data.local.entity.OmokEntity

interface LocalDataSource {
    fun save(omokEntity: OmokEntity): OmokEntity

    fun findAll(): List<OmokEntity>

    fun drop()
}
