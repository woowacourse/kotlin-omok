package woowacourse.omok.data

import woowacourse.omok.domain.stone.Stone

class OmokRepository(
    private val stoneDataSource: StoneDataSource,
) {
    fun insert(stone: Stone) {
        stoneDataSource.insert(StoneDao.valueOf(stone))
    }

    fun findAllStone(): List<Stone> {
        return stoneDataSource.fetchAllStones().map { stoneDao -> stoneDao.toStone() }
    }

    fun removeAll() {
        stoneDataSource.deleteAll()
    }
}
