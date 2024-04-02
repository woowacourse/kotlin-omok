package woowacourse.omok

import android.content.Context
import androidx.core.content.contentValuesOf
import omok.model.Color
import omok.model.Stone

class StoneDao(context: Context) {
    private val dbHelper = OmokDbHelper(context, TABLE_NAME, sql)

    fun insert(stone: Stone) {
        val wb = dbHelper.writableDatabase
        val content =
            contentValuesOf(
                POINT_ROW to stone.point.row,
                POINT_COL to stone.point.col,
                COLOR to if (stone.color == Color.BLACK) BLACK else WHITE,
            )

        wb.insert(
            TABLE_NAME,
            null,
            content,
        )

        wb.close()
    }

    fun stones(): List<Stone> {
        val stones = mutableListOf<StoneEntity>()
        val rd = dbHelper.readableDatabase
        val sql = "select * from $TABLE_NAME"
        val cursor = rd.rawQuery(sql, null)

        while (cursor.moveToNext()) {
            val row = cursor.getInt(cursor.getColumnIndexOrThrow(POINT_ROW))
            val col =
                cursor.getInt(cursor.getColumnIndexOrThrow(POINT_COL))
            val color = cursor.getInt(cursor.getColumnIndexOrThrow(COLOR))
            stones.add(StoneEntity(row, col, color))
        }

        cursor.close()
        rd.close()
        return stones.map { it.toStone() }
    }

    fun deleteAll() {
        val wb = dbHelper.writableDatabase
        wb.delete(TABLE_NAME, null, null)
        wb.close()
    }

    companion object {
        private const val BLACK = 1
        private const val WHITE = 0

        private const val TABLE_NAME = "OMOK"
        private const val POINT_ROW = "point_row"
        private const val POINT_COL = "point_col"
        private const val COLOR = "color"

        private val sql =
            "CREATE TABLE IF NOT EXISTS $TABLE_NAME (\n" +
                "$POINT_ROW int not null,\n" +
                "$POINT_COL int not null,\n" +
                "$COLOR int not null\n" +
                ")"
    }
}
