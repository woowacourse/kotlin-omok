package woowacourse.omok.data.db

import woowacourse.omok.domain.Game
import woowacourse.omok.domain.OmokAdapter
import woowacourse.omok.domain.OmokBoard
import woowacourse.omok.domain.OmokGame
import woowacourse.omok.domain.Stone

class GameDao(
    private val dbHelper: DbHelper,
    private val boardDao: BoardDao,
) {
    fun getOrCreateGame(gameId: Long): OmokGame {
        val stones = boardDao.queryStones(gameId)
        return if (stones.isNotEmpty()) {
            OmokGame(OmokBoard(stones = stones, rule = OmokAdapter()))
        } else {
            OmokGame(OmokBoard(rule = OmokAdapter()))
        }
    }

    fun getStoredStones(gameId: Long): List<Stone> {
        return boardDao.queryStones(gameId)
    }

    fun createGame(roomName: String): Long = dbHelper.insertGame(roomName)

    fun queryGames(): List<Game> = dbHelper.queryGames()

    fun deleteGame(gameId: Int): Boolean = dbHelper.deleteGame(gameId)

}
