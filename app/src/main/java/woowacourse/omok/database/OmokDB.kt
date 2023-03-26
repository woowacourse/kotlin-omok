package woowacourse.omok.database

import android.content.ContentValues
import android.content.Context
import domain.stone.Point

class OmokDB(context: Context) {
    private val db = OmokDBHelper(context).writableDatabase

    fun insertStoneData(point: Point, stoneColor: String) {
        val values = ContentValues()
        values.put(OmokContract.TABLE_COLUMN_X_POINT, point.x)
        values.put(OmokContract.TABLE_COLUMN_Y_POINT, point.y)
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