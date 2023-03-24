package woowacourse.omok

import android.content.ContentValues
import android.content.Context

class OmokDB(context: Context) {
    private val db = OmokDBHelper(context).writableDatabase

    fun insert(point: Pair<Int, Int>, stoneColor: String) {
        val values = ContentValues()
        values.put(OmokContract.TABLE_COLUMN_X_POINT, point.first)
        values.put(OmokContract.TABLE_COLUMN_Y_POINT, point.second)
        values.put(OmokContract.TABLE_COLUMN_STONE_COLOR, stoneColor)
        db.insert(OmokContract.TABLE_NAME, null, values)
    }

    fun getData(): MutableList<Pair<Pair<Int, Int>, String>> {
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
        with(cursor) {
            while (moveToNext()) {
                val x =
                    getInt(getColumnIndexOrThrow(OmokContract.TABLE_COLUMN_X_POINT))
                val y =
                    getInt(getColumnIndexOrThrow(OmokContract.TABLE_COLUMN_Y_POINT))
                val color =
                    getString(getColumnIndexOrThrow(OmokContract.TABLE_COLUMN_STONE_COLOR))
                data.add(Pair(x, y) to color)
            }
            close()
        }

        return data
    }

    fun deleteDB() {
        db.delete(OmokContract.TABLE_NAME, null, null)
    }
}