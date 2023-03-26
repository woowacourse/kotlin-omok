package woowacourse.omok.db

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase

class OmokRepository(
    private val db: SQLiteDatabase
) {
    fun insert(value: ContentValues) {
        db.insert(OmokConstant.TABLE_NAME, null, value)
    }
}