package woowacourse.omok.data.db

import android.content.ContentValues
import android.database.Cursor

class GameDao(
    private val dbHelper: DbHelper,
) {
    fun createGame(roomName: String): Long {
        val db = dbHelper.writableDatabase

        val values =
            ContentValues().apply {
                put(GameContract.COLUMN_NAME_GAME_NAME, roomName)
            }

        val newRowId = db.insert(GameContract.TABLE_NAME, null, values)
        return newRowId
    }

    fun queryGames(): List<Pair<Int, String>> {
        val dbReader = dbHelper.readableDatabase
        val result = mutableListOf<Pair<Int, String>>()

        val cursor: Cursor =
            dbReader.query(
                GameContract.TABLE_NAME,
                arrayOf(
                    GameContract.COLUMN_NAME_GAME_ID,
                    GameContract.COLUMN_NAME_GAME_NAME,
                ),
                null,
                null,
                null,
                null,
                null,
            )

        with(cursor) {
            while (moveToNext()) {
                val gameId = getLong(getColumnIndexOrThrow(GameContract.COLUMN_NAME_GAME_ID))
                val title = getString(getColumnIndexOrThrow(GameContract.COLUMN_NAME_GAME_NAME))
                result.add(Pair(gameId.toInt(), title))
            }
        }
        cursor.close()
        return result
    }

    fun deleteGame(gameId: Int) {
        val db = dbHelper.writableDatabase

        db.delete(
            GameContract.TABLE_NAME,
            "${GameContract.COLUMN_NAME_GAME_ID} = ?",
            arrayOf(gameId.toString()),
        )
    }
}
