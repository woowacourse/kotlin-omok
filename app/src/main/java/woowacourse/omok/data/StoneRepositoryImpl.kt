package woowacourse.omok.data

import woowacourse.omok.domain.model.stone.Stone
import woowacourse.omok.domain.model.stone.StoneType
import woowacourse.omok.domain.model.stone.Stones
import woowacourse.omok.domain.repository.StoneRepository

class StoneRepositoryImpl(private val stoneDao: StoneDao) : StoneRepository {
    override fun insert(stone: Stone) {
        stoneDao.insert(stone)
    }

    override fun lastStoneType(): StoneType = stoneDao.lastStoneType()

    override fun allInBoardSize(size: Int): Stones = Stones(stoneDao.getAll(size))

    override fun clear() {
        stoneDao.clear()
    }
}
