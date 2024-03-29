package woowacourse.omok.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor

class OmokDAO(context: Context) {
    private val dbHelper = DBHelper(context)

    fun insertCoordinate(
        x: Int,
        y: Int,
    ) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("x_column", x)
            put("y_row", y)
        }
        db.insert("omok_coordinates", null, values)
        db.close()
    }

    fun getAllCoordinates(): List<Pair<Int, Int>> {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT x_column, y_row FROM omok_coordinates", null)
        val coordinates = mutableListOf<Pair<Int, Int>>()
        with(cursor) {
            while (moveToNext()) {
                val column = getInt(getColumnIndexOrThrow("x_column"))
                val row = getInt(getColumnIndexOrThrow("y_row"))
                coordinates.add(Pair(column, row))
            }
        }
        cursor.close()
        db.close()
        return coordinates
    }

    fun resetAllCoordinates() {
        val db = dbHelper.writableDatabase
        db.delete("omok_coordinates", null, null)
        db.close()
    }
}
