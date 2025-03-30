package woowacourse.omok.data

import android.content.ContentValues
import woowacourse.omok.data.StoneContract.COLUMN_NAME_COLUMN
import woowacourse.omok.data.StoneContract.COLUMN_NAME_ROW
import woowacourse.omok.data.StoneContract.COLUMN_NAME_STONE_TYPE
import woowacourse.omok.data.StoneContract.TABLE_NAME
import woowacourse.omok.domain.model.stone.Stone
import woowacourse.omok.domain.model.stone.StoneType

class StoneDao(private val dbHelper: OmokDatabaseHelper) {
    fun insert(stone: Stone) {
        val db = dbHelper.writableDatabase
        val values =
            ContentValues().apply {
                put(COLUMN_NAME_COLUMN, stone.position.column.value)
                put(COLUMN_NAME_ROW, stone.position.row.value)
                put(COLUMN_NAME_STONE_TYPE, stone.stoneType.name)
            }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun lastStoneType(): StoneType {
        val db = dbHelper.readableDatabase

        val cursor =
            db.rawQuery(
                "SELECT stone_type, id FROM stones ORDER BY id DESC LIMIT 1",
                null,
            )

        var stoneType: StoneType = StoneType.BLACK
        if (cursor.moveToFirst()) {
            stoneType = StoneType.valueOf(cursor.getString(0)).reverse()
            cursor.close()
            db.close()
        }

        cursor.close()
        db.close()
        return stoneType
    }

    fun getAll(size: Int): List<Stone> {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT y, x, stone_type FROM stones", null)

        val stones = mutableListOf<Stone>()
        if (cursor.moveToFirst()) {
            do {
                val column = cursor.getInt(0)
                val row = cursor.getInt(1)
                val stoneType = StoneType.valueOf(cursor.getString(2))
                stones.add(Stone(column, row, size, stoneType))
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
