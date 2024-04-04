package woowacourse.omok.local.db

import woowacourse.omok.local.model.StoneEntity

class FakeStoneDao : StoneDao {
    private var newId: Long = 0L
    private val values = mutableMapOf<Long, StoneEntity>()

    override fun save(stoneEntity: StoneEntity): StoneEntity {
        val id = ++newId
        val newStone = stoneEntity.copy(id = id)
        values[id] = newStone
        return newStone
    }

    override fun findAll(): List<StoneEntity> {
        return values.values.toList()
    }

    override fun drop() {
        values.clear()
    }
}
