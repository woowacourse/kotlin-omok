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
            put(OmockDBConstant.X_COORDINATE, x)
            put(OmockDBConstant.Y_COORDINATE, y)
        }
        db.insert(OmockDBConstant.TABLE_NAME, null, values)
        db.close()
    }

    fun getAllCoordinates(): List<Pair<Int, Int>> {
        val db = dbHelper.readableDatabase
        val cursor: Cursor =
            db.rawQuery(
                "SELECT ${OmockDBConstant.X_COORDINATE},${OmockDBConstant.Y_COORDINATE} FROM omok_coordinates",
                null,
            )
        val coordinates = mutableListOf<Pair<Int, Int>>()
        with(cursor) {
            while (moveToNext()) {
                val column = getInt(getColumnIndexOrThrow(OmockDBConstant.X_COORDINATE))
                val row = getInt(getColumnIndexOrThrow(OmockDBConstant.Y_COORDINATE))
                coordinates.add(Pair(column, row))
            }
        }
        cursor.close()
        db.close()
        return coordinates
    }

    fun resetAllCoordinates() {
        val db = dbHelper.writableDatabase
        db.delete(OmockDBConstant.TABLE_NAME, null, null)
        db.close()
    }
}
