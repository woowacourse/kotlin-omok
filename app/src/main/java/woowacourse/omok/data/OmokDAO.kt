package woowacourse.omok.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase

class OmokDAO(context: Context) {
    private val dbHelper = OmokDbHelper(context)
    private val db = dbHelper.writableDatabase

    fun saveStone(x: Int, y: Int, stone: String) {
        val values = ContentValues().apply {
            put("x", x)
            put("y", y)
            put("stone", stone)
        }
        db.insert(OmokDbHelper.TABLE_NAME, null, values)
    }

    fun loadAllStones(): List<Triple<Int, Int, String>> {
        val result = mutableListOf<Triple<Int, Int, String>>()
        db.query(OmokDbHelper.TABLE_NAME, arrayOf("x", "y", "stone"), null, null, null, null, null).use {
            while (it.moveToNext()) {
                val x = it.getInt(it.getColumnIndexOrThrow("x"))
                val y = it.getInt(it.getColumnIndexOrThrow("y"))
                val stone = it.getString(it.getColumnIndexOrThrow("stone"))
                result.add(Triple(x, y, stone))
            }
        }
        return result
    }

    fun clearBoard() {
        db.delete(OmokDbHelper.TABLE_NAME, null, null)
        db.delete("turn", null, null)
    }

    fun saveTurn(stone: String) {
        val values = ContentValues().apply {
            put("id", 0)
            put("current_turn", stone)
        }

        db.insertWithOnConflict(
            "turn",
            null,
            values,
            SQLiteDatabase.CONFLICT_REPLACE
        )
    }

    fun loadTurn(): String? {
        val cursor = db.query(
            "turn",
            arrayOf("current_turn"),
            "id = ?",
            arrayOf("0"),
            null, null, null
        )

        val turn = if (cursor.moveToFirst()) {
            cursor.getString(cursor.getColumnIndexOrThrow("current_turn"))
        } else null

        cursor.close()
        return turn
    }
}
