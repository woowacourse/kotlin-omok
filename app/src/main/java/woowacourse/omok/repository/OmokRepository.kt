package woowacourse.omok.repository

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import woowacourse.omok.database.OmokContract.TABLE_COLUMN_ID
import woowacourse.omok.database.OmokContract.TABLE_NAME

class OmokRepository(val database: SQLiteDatabase) {

    private var cursor: Cursor? = null

    fun insert(values: ContentValues) {
        database.insert(TABLE_NAME, null, values)
    }

    fun getAll(): Cursor {
        val query = "SELECT * FROM $TABLE_NAME;"
        return database.rawQuery(query, null)
    }

    fun reset() {
        val query = "DELETE FROM $TABLE_NAME"
        database.execSQL(query)
    }

    fun isEmpty(): Boolean {
        val query = "SELECT * FROM $TABLE_NAME"
        return database.rawQuery(query, null).use { it.count == 0 }
    }

    fun close() {
        database.close()
    }

    fun getLast(): Cursor {
        val query = "SELECT * FROM $TABLE_NAME ORDER BY $TABLE_COLUMN_ID DESC LIMIT 1"
        return database.rawQuery(query, null)
    }
}
