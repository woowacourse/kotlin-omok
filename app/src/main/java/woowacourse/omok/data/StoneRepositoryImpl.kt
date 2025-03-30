package woowacourse.omok.data

import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.stone.Stone
import woowacourse.omok.domain.model.stone.StoneType
import woowacourse.omok.domain.model.stone.Stones
import woowacourse.omok.domain.repository.StoneRepository

class StoneRepositoryImpl(private val stoneDao: StoneDao) : StoneRepository {
    override fun insert(stone: Stone) {
        stoneDao.insert(stone.toStoneEntity())
    }

    override fun lastStoneType(board: Board): StoneType = stoneDao.lastStone()?.toStone(board)?.stoneType ?: StoneType.WHITE

    override fun allInBoardSize(board: Board): Stones = Stones(stoneDao.getAll().map { it.toStone(board) })

    override fun clear() {
        stoneDao.clear()
    }
}
