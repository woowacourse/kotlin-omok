package woowacourse.omok.repository

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import woowacourse.omok.database.OmokConstact.TABLE_NAME

class OmokRepository(val database: SQLiteDatabase) {

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
        val cursor = database.rawQuery(query, null)
        return cursor.count == 0
    }

    fun close() {
        database.close()
    }
}
