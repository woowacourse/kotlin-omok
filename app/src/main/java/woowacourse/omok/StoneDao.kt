package woowacourse.omok

import android.content.Context
import androidx.core.content.contentValuesOf
import omok.model.Color
import omok.model.Stone

class StoneDao(context: Context) {
    private val dbHelper = StoneDbHelper(context)

    fun insert(stone: Stone) {
        val wb = dbHelper.writableDatabase
        val content =
            with(dbHelper) {
                contentValuesOf(
                    pointRow to stone.point.row,
                    pointCol to stone.point.col,
                    color to if (stone.color == Color.BLACK) BLACK else WHITE,
                )
            }

        wb.insert(
            dbHelper.tableName,
            null,
            content,
        )

        wb.close()
    }

    fun stones(): List<Stone> {
        val stones = mutableListOf<StoneEntity>()
        val rd = dbHelper.readableDatabase
        val sql = "select * from ${dbHelper.tableName}"
        val cursor = rd.rawQuery(sql, null)

        while (cursor.moveToNext()) {
            val row = cursor.getInt(cursor.getColumnIndexOrThrow(dbHelper.pointRow))
            val col =
                cursor.getInt(cursor.getColumnIndexOrThrow(dbHelper.pointCol))
            val color = cursor.getInt(cursor.getColumnIndexOrThrow(dbHelper.color))
            stones.add(StoneEntity(row, col, color))
        }

        cursor.close()
        rd.close()
        return stones.map { it.toStone() }
    }

    fun deleteAll() {
        val wb = dbHelper.writableDatabase
        wb.delete(dbHelper.tableName, null, null)
        wb.close()
    }

    companion object {
        private const val BLACK = 1
        private const val WHITE = 0
    }
}
