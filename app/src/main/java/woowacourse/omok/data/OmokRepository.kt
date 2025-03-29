package woowacourse.omok.data

import woowacourse.omok.domain.position.Position
import woowacourse.omok.domain.stone.Stone

class OmokRepository(
    private val stoneDataSource: StoneDataSource,
) {
    fun insert(stone: Stone): Boolean {
        val findStone = findStoneByPosition(stone.position)
        findStone?.let { return false } ?: stoneDataSource.insert(StoneDao.valueOf(stone))
        return true
    }

    fun findStoneByPosition(position: Position): Stone? {
        return stoneDataSource.fetchStoneByPosition(position)?.toStone()
    }

    fun findAllStone(): List<Stone> {
        return stoneDataSource.fetchAllStones().map { stoneDao -> stoneDao.toStone() }
    }

    fun removeAll() {
        stoneDataSource.deleteAll()
    }
}
