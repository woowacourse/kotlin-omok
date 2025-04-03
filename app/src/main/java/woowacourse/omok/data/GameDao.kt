package woowacourse.omok.data

import android.content.ContentValues
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.omok.data.GameContract.COLUMN_NAME_NAME
import woowacourse.omok.data.GameContract.TABLE_NAME

class GameDao(private val dbHelper: SQLiteOpenHelper) {
    fun insert(name: String) =
        dbHelper.writableDatabase.use {
            val values =
                ContentValues().apply {
                    put(COLUMN_NAME_NAME, name)
                }
            it.insert(TABLE_NAME, null, values)
        }

    fun getAll(): List<GameEntity> =
        dbHelper.readableDatabase.use { database ->
            val cursor = database.rawQuery("SELECT _id, name FROM games", null)

            cursor.use {
                it.run {
                    val games = mutableListOf<GameEntity>()
                    if (moveToFirst()) {
                        do {
                            val id = getLong(0)
                            val name = getString(1)
                            games.add(GameEntity(id, name))
                        } while (moveToNext())
                    }
                    games
                }
            }
        }

    fun delete(id: Long) =
        dbHelper.writableDatabase.use { database ->
            database.delete(
                TABLE_NAME,
                "_id = ?",
                arrayOf(id.toString()),
            )
        }
}
