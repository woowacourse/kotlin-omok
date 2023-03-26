package woowacourse.omok.database

import android.content.ContentValues
import android.content.Context
import domain.stone.Point

class OmokDB(context: Context) {
    private val db = OmokDBHelper(context).writableDatabase

    fun insertStoneData(point: Point, stoneColor: String) {
        val values = ContentValues()
        values.put(OmokDBNames.TABLE_COLUMN_X_POINT, point.x)
        values.put(OmokDBNames.TABLE_COLUMN_Y_POINT, point.y)
        values.put(OmokDBNames.TABLE_COLUMN_STONE_COLOR, stoneColor)
        db.insert(OmokDBNames.TABLE_NAME, null, values)
    }

    fun getStoneData(): MutableList<StoneData> {
        val data = mutableListOf<StoneData>()
        val cursor = db.query(
            OmokDBNames.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )

        while (cursor.moveToNext()) {
            val x =
                cursor.getInt(cursor.getColumnIndexOrThrow(OmokDBNames.TABLE_COLUMN_X_POINT))
            val y =
                cursor.getInt(cursor.getColumnIndexOrThrow(OmokDBNames.TABLE_COLUMN_Y_POINT))
            val color =
                cursor.getString(cursor.getColumnIndexOrThrow(OmokDBNames.TABLE_COLUMN_STONE_COLOR))
            data.add(StoneData(Point(x, y), color))
        }
        cursor.close()

        return data
    }

    fun deleteDB() {
        db.delete(OmokDBNames.TABLE_NAME, null, null)
    }
}