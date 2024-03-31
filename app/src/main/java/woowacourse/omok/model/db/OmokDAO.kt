package woowacourse.omok.model.db

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import woowacourse.omok.model.db.OmokContract.OmokEntry

class OmokDAO(private val db: SQLiteDatabase) {
    fun insert(
        x: Int,
        y: Int,
        turn: String,
    ): Long {
        val values = ContentValues()
        values.put(OmokEntry.POINT_X, x)
        values.put(OmokEntry.POINT_Y, y)
        values.put(OmokEntry.TURN, turn)

        return db.insert(OmokEntry.TABLE_NAME, null, values)
    }

    fun deleteAll() {
        db.execSQL("DELETE FROM ${OmokEntry.TABLE_NAME}")
    }
}
