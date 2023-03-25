package woowacourse.omok.database.repository

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import domain.point.Point
import woowacourse.omok.database.contract.POINT_TABLE_NAME

class OmokRepository(private val database: SQLiteDatabase) : Repository<Point> {
    override fun getAll(): List<Point> = mutableListOf<Point>().apply {
        database.rawQuery("SELECT * FROM $POINT_TABLE_NAME", null).use {
            while (it.moveToNext()) {
                val col = it.getInt(it.getColumnIndexOrThrow("x"))
                val row = it.getInt(it.getColumnIndexOrThrow("y"))
                add(Point(row, col))
            }
        }
    }

    override fun insert(item: Point) {
        val record = ContentValues().apply {
            put("x", item.col)
            put("y", item.row)
        }
        database.insert(POINT_TABLE_NAME, null, record)
    }

    override fun isEmpty(): Boolean =
        database.rawQuery("SELECT * FROM $POINT_TABLE_NAME", null).use {
            it.count == 0
        }

    override fun clear() {
        database.execSQL("DELETE FROM $POINT_TABLE_NAME")
    }

    override fun close() {
        database.close()
    }
}
