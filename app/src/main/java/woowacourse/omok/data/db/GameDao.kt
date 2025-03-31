package woowacourse.omok.data.db

import woowacourse.omok.domain.Game

class GameDao(
    private val dbHelper: DbHelper,
) {
    fun createGame(roomName: String): Long = dbHelper.insertGame(roomName)

    fun queryGames(): List<Game> = dbHelper.queryGames()

    fun deleteGame(gameId: Int) {
        dbHelper.deleteGame(gameId)
    }
}
