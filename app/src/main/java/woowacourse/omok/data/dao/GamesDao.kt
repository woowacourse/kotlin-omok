package woowacourse.omok.data.dao

import android.content.ContentValues
import woowacourse.omok.data.GamesTableContract
import woowacourse.omok.data.MovesTableContract
import woowacourse.omok.data.OmokDatabaseHelper
import woowacourse.omok.domain.Game

class GamesDao(
    private val dbHelper: OmokDatabaseHelper,
) {
    fun createGame(title: String): Result<Int> =
        dbHelper.safeDatabaseOperation { db ->
            val values =
                ContentValues().apply {
                    put(GamesTableContract.COLUMN_NAME_TITLE, title)
                    put(GamesTableContract.COLUMN_NAME_STATUS, GamesTableContract.VALUE_GAME_STATUS_PLAYING)
                }
            val gameId = db.insert(GamesTableContract.TABLE_NAME, null, values)
            if (gameId == -1L) throw Exception("게임 생성 실패")
            gameId.toInt()
        }

    fun deleteGame(gameId: Int): Result<Unit> =
        dbHelper.safeDatabaseOperation { db ->
            db.delete(
                MovesTableContract.TABLE_NAME,
                "${MovesTableContract.COLUMN_NAME_GAME_ID_FK} = ?",
                arrayOf(gameId.toString()),
            )
            db.delete(
                GamesTableContract.TABLE_NAME,
                "${GamesTableContract.COLUMN_NAME_ID} = ?",
                arrayOf(gameId.toString()),
            )
            Unit
        }

    fun updateGameStatus(gameId: Int): Result<Unit> =
        dbHelper.safeDatabaseOperation { db ->
            val values =
                ContentValues().apply {
                    put(GamesTableContract.COLUMN_NAME_STATUS, GamesTableContract.VALUE_GAME_STATUS_FINISHED)
                }
            val rowsUpdated =
                db.update(
                    GamesTableContract.TABLE_NAME,
                    values,
                    "${GamesTableContract.COLUMN_NAME_ID} = ?",
                    arrayOf(gameId.toString()),
                )
            if (rowsUpdated == 0) throw Exception("게임 상태 업데이트 실패")
            Unit
        }

    fun getGames(): Result<List<Game>> =
        dbHelper.safeDatabaseOperation { db ->
            val cursor =
                db.query(
                    GamesTableContract.TABLE_NAME,
                    arrayOf(
                        GamesTableContract.COLUMN_NAME_ID,
                        GamesTableContract.COLUMN_NAME_TITLE,
                        GamesTableContract.COLUMN_NAME_STATUS,
                    ),
                    null,
                    null,
                    null,
                    null,
                    null,
                )
            val games = mutableListOf<Game>()
            cursor.use {
                while (it.moveToNext()) {
                    val game =
                        Game(
                            it.getInt(0),
                            it.getString(1),
                            it.getInt(2) == GamesTableContract.VALUE_GAME_STATUS_FINISHED,
                        )
                    games.add(game)
                }
            }
            games
        }
}
