package woowacourse.omok.database.stoneposition

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class StonePositionHandler {

    fun deleteAllColumns(db: SQLiteDatabase) {
        db.delete(StonePositionConstract.TABLE_NAME, "", arrayOf())
    }

    fun getCursor(db: SQLiteDatabase): Cursor {
        return db.query(
            StonePositionConstract.TABLE_NAME,
            arrayOf(
                StonePositionConstract.TABLE_COLUMN_ROW,
                StonePositionConstract.TABLE_COLUMN_COLUMN
            ),
            "", arrayOf(), null, null, ""
        )
    }
}
