package woowacourse.omok.domain

import woowacourse.omok.data.db.BoardDao

class BoardService(
    private val boardDao: BoardDao,
) {
    fun loadBoard(gameId: Long): OmokBoard {
        val stones = boardDao.queryStones(gameId)
        return if (stones.isNotEmpty()) {
            OmokBoard(stones = stones, rule = OmokAdapter())
        } else {
            OmokBoard(rule = OmokAdapter())
        }
    }
}