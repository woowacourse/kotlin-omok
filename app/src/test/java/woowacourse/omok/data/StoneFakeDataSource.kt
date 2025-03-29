package woowacourse.omok.data

import woowacourse.omok.domain.position.Position
import woowacourse.omok.domain.stone.Stone

class StoneFakeDataSource : StoneDataSource {
    private val _stones = mutableListOf<Stone>()
    val stones get() = _stones.toList()

    init {
        _stones.clear()
    }

    override fun insert(stoneDao: StoneDao) {
        _stones.add(stoneDao.toStone())
    }

    override fun fetchStoneByPosition(position: Position): StoneDao? {
        val findStone = stones.firstOrNull { stone -> stone.position.isSame(position) } ?: return null
        return StoneDao.valueOf(findStone)
    }

    override fun fetchAllStones(): List<StoneDao> {
        val stoneDaos = stones.map { stone -> StoneDao.valueOf(stone) }
        return stoneDaos
    }

    override fun deleteAll() {
        _stones.clear()
    }
}
