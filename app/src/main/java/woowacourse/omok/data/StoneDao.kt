package woowacourse.omok.data

import android.content.ContentValues
import woowacourse.omok.data.StoneContract.COLUMN_NAME_COLUMN
import woowacourse.omok.data.StoneContract.COLUMN_NAME_ROW
import woowacourse.omok.data.StoneContract.COLUMN_NAME_STONE_TYPE
import woowacourse.omok.data.StoneContract.TABLE_NAME

class StoneDao(private val dbHelper: OmokDatabaseHelper) {
    fun insert(stone: StoneEntity) {
        val db = dbHelper.writableDatabase
        val values =
            ContentValues().apply {
                put(COLUMN_NAME_COLUMN, stone.y)
                put(COLUMN_NAME_ROW, stone.x)
                put(COLUMN_NAME_STONE_TYPE, stone.stoneType)
            }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun lastStone(): StoneEntity? {
        val db = dbHelper.readableDatabase

        val cursor =
            db.rawQuery(
                "SELECT y, x ,stone_type, _id FROM stones ORDER BY _id DESC LIMIT 1",
                null,
            )

        var stoneEntity: StoneEntity? = null
        if (cursor.moveToFirst()) {
            val column = cursor.getInt(0)
            val row = cursor.getInt(1)
            val stoneType = cursor.getString(2)
            stoneEntity = StoneEntity(column, row, stoneType)
            cursor.close()
            db.close()
        }

        cursor.close()
        db.close()
        return stoneEntity
    }

    fun getAll(): List<StoneEntity> {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT y, x, stone_type FROM stones", null)

        val stones = mutableListOf<StoneEntity>()
        if (cursor.moveToFirst()) {
            do {
                val column = cursor.getInt(0)
                val row = cursor.getInt(1)
                val stoneType = cursor.getString(2)
                stones.add(StoneEntity(column, row, stoneType))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return stones
    }

    fun clear() {
        val db = dbHelper.writableDatabase
        db.execSQL("DELETE FROM $TABLE_NAME")
        db.close()
    }
}
