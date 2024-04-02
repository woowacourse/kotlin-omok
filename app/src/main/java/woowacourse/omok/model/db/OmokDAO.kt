package woowacourse.omok.model.db

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import woowacourse.omok.model.db.OmokContract.OmokEntry
import woowacourse.omok.model.entity.Point
import woowacourse.omok.model.entity.Stone
import woowacourse.omok.model.entity.StoneColor

class OmokDAO(
    private val db: SQLiteDatabase,
    private val tableName: String = OmokEntry.TABLE_NAME,
) {
    fun insertStone(
        x: Int,
        y: Int,
        color: String,
    ): Long {
        val values = ContentValues()
        values.put(OmokEntry.POINT_X, x)
        values.put(OmokEntry.POINT_Y, y)
        values.put(OmokEntry.COLOR, color)

        return db.insert(tableName, null, values)
    }

    fun deleteAll() {
        db.execSQL("DELETE FROM $tableName")
    }

    fun selectAll(): Set<Stone> {
        val stones = mutableSetOf<Stone>()
        val cursor =
            db.query(
                OmokEntry.TABLE_NAME,
                arrayOf(
                    OmokEntry.POINT_X,
                    OmokEntry.POINT_Y,
                    OmokEntry.COLOR,
                ),
                null,
                null,
                null,
                null,
                null,
            )

        while (cursor.moveToNext()) {
            val x = cursor.getString(cursor.getColumnIndexOrThrow(OmokEntry.POINT_X))
            val y = cursor.getString(cursor.getColumnIndexOrThrow(OmokEntry.POINT_Y))
            val turn = cursor.getString(cursor.getColumnIndexOrThrow(OmokEntry.COLOR))

            stones.add(
                Stone(
                    Point(x.toInt(), y.toInt()),
                    if (turn == "BLACK") StoneColor.BLACK else StoneColor.WHITE,
                ),
            )
        }

        return stones.toSet()
    }
}
