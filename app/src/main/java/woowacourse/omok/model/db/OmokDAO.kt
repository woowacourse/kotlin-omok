package woowacourse.omok.model.db

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase

class OmokDAO(private val db: SQLiteDatabase) {
    fun insert(
        x: Int,
        y: Int,
        turn: String,
    ): Long {
        val values = ContentValues()
        values.put(OmokContract.OmokEntry.POINT_X, x)
        values.put(OmokContract.OmokEntry.POINT_Y, y)
        values.put(OmokContract.OmokEntry.TURN, turn)

        return db.insert(OmokContract.OmokEntry.TABLE_NAME, null, values)
    }
}
