package woowacourse.omok.data.dao

import android.content.ContentValues
import woowacourse.omok.data.GamesTableContract
import woowacourse.omok.data.MovesTableContract
import woowacourse.omok.data.OmokDatabaseHelper

class GamesDao(
    private val dbHelper: OmokDatabaseHelper,
) {
    fun addGame(gameId: Int) {
        dbHelper.writableDatabase.use { db ->
            val values =
                ContentValues().apply {
                    put(GamesTableContract.COLUMN_NAME_ID, gameId)
                }
            db.insert(GamesTableContract.TABLE_NAME, null, values)
        }
    }

    fun deleteGame(gameId: Int) {
        dbHelper.writableDatabase.use { db ->
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
        }
    }

    fun getGameIds(): List<Int> {
        val db = dbHelper.readableDatabase
        val cursor =
            db.query(
                GamesTableContract.TABLE_NAME,
                arrayOf(GamesTableContract.COLUMN_NAME_ID),
                null,
                null,
                null,
                null,
                null,
            )

        return cursor.use {
            val gameIds = mutableListOf<Int>()
            while (it.moveToNext()) {
                gameIds.add(it.getInt(0))
            }
            gameIds
        }
    }
}
