package woowacourse.omok.local.db

import woowacourse.omok.local.model.StoneEntity

interface StoneDao {
    fun save(stoneEntity: StoneEntity): StoneEntity

    fun findAll(): List<StoneEntity>

    fun drop()
}
