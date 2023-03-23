package woowacourse.omok.database.repository

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import woowacourse.omok.database.contract.POINT_TABLE_NAME

class OmokRepository(private val database: SQLiteDatabase) : Repository {
    override fun getAll(action: (Cursor) -> Unit) {
        database.rawQuery("SELECT * FROM $POINT_TABLE_NAME", null).use { action(it) }
    }

    override fun insert(record: ContentValues) {
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
