package woowacourse.omok.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase

class OmokDbController(context: Context) {
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
        val cursor = db.query(OmokDbHelper.TABLE_NAME, arrayOf("x", "y", "stone"), null, null, null, null, null)

        while (cursor.moveToNext()) {
            val x = cursor.getInt(cursor.getColumnIndexOrThrow("x"))
            val y = cursor.getInt(cursor.getColumnIndexOrThrow("y"))
            val stone = cursor.getString(cursor.getColumnIndexOrThrow("stone"))
            result.add(Triple(x, y, stone))
        }
        cursor.close()
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
