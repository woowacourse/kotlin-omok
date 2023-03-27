package woowacourse.omok.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import woowacourse.omok.db.OmokConstant.TABLE_COLUMN_COLOR
import woowacourse.omok.db.OmokConstant.TABLE_COLUMN_X
import woowacourse.omok.db.OmokConstant.TABLE_COLUMN_Y
import woowacourse.omok.db.OmokConstant.TABLE_NAME

class OmokRepository(
    private val context: Context,
    private val db: SQLiteDatabase = OmokDBHelper(context).writableDatabase
) {
    val cursor: Cursor
        get() = db.query(
            TABLE_NAME,
            arrayOf(
                TABLE_COLUMN_X,
                TABLE_COLUMN_Y,
                TABLE_COLUMN_COLOR
            ),
            null,
            null,
            null,
            null,
            null
        )

    fun insert(value: ContentValues) {
        db.insert(TABLE_NAME, null, value)
    }

    fun clear() {
        db.execSQL("DELETE FROM $TABLE_NAME")
    }
}
