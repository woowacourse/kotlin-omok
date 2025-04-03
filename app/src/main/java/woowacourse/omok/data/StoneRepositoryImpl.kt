package woowacourse.omok.data

import woowacourse.omok.domain.model.Board
import woowacourse.omok.domain.model.stone.Stone
import woowacourse.omok.domain.model.stone.StoneType
import woowacourse.omok.domain.model.stone.Stones
import woowacourse.omok.domain.repository.StoneRepository

class StoneRepositoryImpl(private val stoneDao: StoneDao) : StoneRepository {
    override fun insert(
        gameId: Long,
        stone: Stone,
    ) {
        stoneDao.insert(gameId, stone.toStoneEntity())
    }

    override fun lastStoneType(
        gameId: Long,
        board: Board,
    ): StoneType = stoneDao.lastStone(gameId)?.toStone(board)?.stoneType ?: StoneType.WHITE

    override fun allInBoardSize(
        gameId: Long,
        board: Board,
    ): Stones = Stones(stoneDao.getAll(gameId).map { it.toStone(board) })

    override fun clear(gameId: Long) {
        stoneDao.clear(gameId)
    }
}
