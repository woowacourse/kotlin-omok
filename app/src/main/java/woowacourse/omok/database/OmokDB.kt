package woowacourse.omok.database

import android.content.ContentValues
import android.content.Context

class OmokDB(context: Context) {
    private val db = OmokDBHelper(context).writableDatabase

    fun insertStoneData(point: Pair<Int, Int>, stoneColor: String) {
        val values = ContentValues()
        values.put(OmokContract.TABLE_COLUMN_X_POINT, point.first)
        values.put(OmokContract.TABLE_COLUMN_Y_POINT, point.second)
        values.put(OmokContract.TABLE_COLUMN_STONE_COLOR, stoneColor)
        db.insert(OmokContract.TABLE_NAME, null, values)
    }

    fun getStoneData(): MutableList<Pair<Pair<Int, Int>, String>> {
        val data = mutableListOf<Pair<Pair<Int, Int>, String>>()
        val cursor = db.query(
            OmokContract.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )

        while (cursor.moveToNext()) {
            val x =
                cursor.getInt(cursor.getColumnIndexOrThrow(OmokContract.TABLE_COLUMN_X_POINT))
            val y =
                cursor.getInt(cursor.getColumnIndexOrThrow(OmokContract.TABLE_COLUMN_Y_POINT))
            val color =
                cursor.getString(cursor.getColumnIndexOrThrow(OmokContract.TABLE_COLUMN_STONE_COLOR))
            data.add(Pair(x, y) to color)
        }
        cursor.close()

        return data
    }

    fun deleteDB() {
        db.delete(OmokContract.TABLE_NAME, null, null)
    }
}