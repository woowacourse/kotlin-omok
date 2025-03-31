package woowacourse.omok.data

import android.content.ContentValues
import woowacourse.omok.data.StoneContract.COLUMN_NAME_COLUMN
import woowacourse.omok.data.StoneContract.COLUMN_NAME_ROW
import woowacourse.omok.data.StoneContract.COLUMN_NAME_STONE_TYPE
import woowacourse.omok.data.StoneContract.TABLE_NAME

class StoneDao(private val dbHelper: OmokDatabaseHelper) {
    fun insert(stone: StoneEntity) =
        dbHelper.writableDatabase.use {
            val values =
                ContentValues().apply {
                    put(COLUMN_NAME_COLUMN, stone.y)
                    put(COLUMN_NAME_ROW, stone.x)
                    put(COLUMN_NAME_STONE_TYPE, stone.stoneType)
                }
            it.insert(TABLE_NAME, null, values)
        }

    fun lastStone(): StoneEntity? =
        dbHelper.readableDatabase.use { database ->
            val cursor =
                database.rawQuery(
                    "SELECT y, x ,stone_type, _id FROM stones ORDER BY _id DESC LIMIT 1",
                    null,
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

    fun getAll(): List<StoneEntity> =
        dbHelper.readableDatabase.use { database ->
            val cursor = database.rawQuery("SELECT y, x, stone_type FROM stones", null)

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

    fun clear() =
        dbHelper.writableDatabase.use { database ->
            database.execSQL("DELETE FROM $TABLE_NAME")
        }
}
