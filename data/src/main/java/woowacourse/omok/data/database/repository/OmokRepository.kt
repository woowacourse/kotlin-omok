package woowacourse.omok.data.database.repository

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import domain.point.Point
import repository.Repository
import woowacourse.omok.data.database.contract.POINT_TABLE_NAME
import woowacourse.omok.data.database.entity.PointData
import woowacourse.omok.data.database.mapper.toData
import woowacourse.omok.data.database.mapper.toDomain

class OmokRepository(private val database: SQLiteDatabase) : Repository<Point> {
    override suspend fun getAll(): List<Point> = mutableListOf<Point>().apply {
        database.rawQuery("SELECT * FROM $POINT_TABLE_NAME", null).use {
            while (it.moveToNext()) {
                val x = it.getInt(it.getColumnIndexOrThrow("x"))
                val y = it.getInt(it.getColumnIndexOrThrow("y"))
                add(PointData(x = x, y = y).toDomain())
            }
        }
    }

    override fun insert(item: Point) {
        val mappedItem = item.toData()
        val record = ContentValues().apply {
            put("x", mappedItem.x)
            put("y", mappedItem.y)
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
