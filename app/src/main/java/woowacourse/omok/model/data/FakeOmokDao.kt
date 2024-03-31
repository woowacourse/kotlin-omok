package woowacourse.omok.model.data

class FakeOmokDao : OmokDao {
    private var id = 0L
    private val values = mutableMapOf<Long, OmokEntity>()

    override fun save(omokEntity: OmokEntity): OmokEntity {
        values[id++] = omokEntity
        return omokEntity
    }

    override fun findAll(): List<OmokEntity> {
        return values.map { it.value }
    }

    override fun drop() {
        values.clear()
    }
}
