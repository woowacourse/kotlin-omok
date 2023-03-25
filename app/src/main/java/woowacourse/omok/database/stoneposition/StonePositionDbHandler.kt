package woowacourse.omok.database.stoneposition

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import domain.stone.StonePosition

class StonePositionDbHandler {

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

    fun addColumn(db: SQLiteDatabase, stonePosition: StonePosition) {
        val values = ContentValues().apply {
            put(StonePositionConstract.TABLE_COLUMN_ROW, stonePosition.y)
            put(StonePositionConstract.TABLE_COLUMN_COLUMN, stonePosition.x)
        }

        db.insert(StonePositionConstract.TABLE_NAME, null, values)
    }
}
