package woowacourse.omok.data

import android.content.ContentValues
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.omok.data.StoneContract.COLUMN_GAME_ID
import woowacourse.omok.data.StoneContract.COLUMN_NAME_COLUMN
import woowacourse.omok.data.StoneContract.COLUMN_NAME_ROW
import woowacourse.omok.data.StoneContract.COLUMN_NAME_STONE_TYPE
import woowacourse.omok.data.StoneContract.TABLE_NAME

class StoneDao(private val dbHelper: SQLiteOpenHelper) {
    fun insert(
        gameId: Long,
        stone: StoneEntity,
    ) = dbHelper.writableDatabase.use {
        val values =
            ContentValues().apply {
                put(COLUMN_GAME_ID, gameId)
                put(COLUMN_NAME_COLUMN, stone.y)
                put(COLUMN_NAME_ROW, stone.x)
                put(COLUMN_NAME_STONE_TYPE, stone.stoneType)
            }
        it.insert(TABLE_NAME, null, values)
    }

    fun lastStone(gameId: Long): StoneEntity? =
        dbHelper.readableDatabase.use { database ->
            val cursor =
                database.rawQuery(
                    "SELECT y, x ,stone_type FROM stones WHERE game_id = ? ORDER BY _id DESC LIMIT 1",
                    arrayOf(gameId.toString()),
                )
            cursor.use {
                it.run {
                    if (moveToFirst()) {
                        val column = getInt(0)
                        val row = getInt(1)
                        val stoneType = getString(2)
                        StoneEntity(column, row, stoneType)
                    } else {
                        null
                    }
                }
            }
        }

    fun getAll(gameId: Long): List<StoneEntity> =
        dbHelper.readableDatabase.use { database ->
            val cursor =
                database.rawQuery("SELECT y, x, stone_type FROM stones WHERE game_id = ?", arrayOf(gameId.toString()))

            cursor.use {
                it.run {
                    val stones = mutableListOf<StoneEntity>()
                    if (moveToFirst()) {
                        do {
                            val column = getInt(0)
                            val row = getInt(1)
                            val stoneType = getString(2)
                            stones.add(StoneEntity(column, row, stoneType))
                        } while (moveToNext())
                    }
                    stones
                }
            }
        }

    fun clear(gameId: Long) =
        dbHelper.writableDatabase.use { database ->
            database.delete(TABLE_NAME, "game_id = ?", arrayOf(gameId.toString()))
        }
}
