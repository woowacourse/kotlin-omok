package woowacourse.omok.db

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import woowacourse.omok.db.OmokConstant.TABLE_NAME

class OmokRepository(
    private val db: SQLiteDatabase
) {
    fun insert(value: ContentValues) {
        db.insert(TABLE_NAME, null, value)
    }

    fun clear() {
        db.execSQL("DELETE FROM $TABLE_NAME")
    }
}