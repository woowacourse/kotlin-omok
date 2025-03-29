package woowacourse.omok.data

import android.content.ContentValues
import woowacourse.omok.domain.model.position.Stone
import woowacourse.omok.domain.model.stone.StoneType

class StoneDao(private val dbHelper: OmokDatabaseHelper) {
    fun insert(stone: Stone) {
        val db = dbHelper.writableDatabase
        val values =
            ContentValues().apply {
                put("x", stone.position.column.value)
                put("y", stone.position.row.value)
                put("stone_type", stone.stoneType.name)
            }
        db.insert("stones", null, values)
        db.close()
    }

    fun getAll(size: Int): List<Stone> {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT x, y, stone_type FROM stones", null)

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
        db.execSQL("DELETE FROM stones")
        db.close()
    }
}
