package woowacourse.omok.data.db

import woowacourse.omok.domain.GameRoom

class GameDao(
    private val dbHelper: DbHelper,
) {
    fun createGame(roomName: String): Long = dbHelper.insertGame(roomName)

    fun queryGames(): List<GameRoom> = dbHelper.queryGames()

    fun deleteGame(gameId: Int): Boolean = dbHelper.deleteGame(gameId)
}
