package woowacourse.omok.data

import woowacourse.omok.domain.stone.Stone

class StoneFakeDataSource : StoneDataSource {
    private val _stones = mutableListOf<Stone>()
    val stones get() = _stones.toList()

    init {
        _stones.clear()
    }

    override fun fetchAllStones(): List<StoneDao> {
        val stoneDaos = stones.map { stone -> StoneDao.valueOf(stone) }
        return stoneDaos
    }

    override fun insert(stoneDao: StoneDao) {
        _stones.add(stoneDao.toStone())
    }

    override fun deleteAll() {
        _stones.clear()
    }
}
