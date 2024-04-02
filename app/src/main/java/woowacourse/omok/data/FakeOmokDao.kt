package woowacourse.omok.data

import woowacourse.omok.data.`interface`.Dao

class FakeOmokDao : Dao<OmokEntity> {
    private var id = 0L
    private val values = mutableMapOf<Long, OmokEntity>()

    override fun save(entity: OmokEntity): OmokEntity {
        values[id++] = entity
        return entity
    }

    override fun findAll(): List<OmokEntity> {
        return values.map { it.value }
    }

    override fun findLastOrNull(): OmokEntity? {
        return findAll().maxByOrNull { it.id }
    }

    override fun deleteAll() {
        values.clear()
    }
}
