package woowacourse.omok

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.omok.FeedReaderContract.SQL_CREATE_ENTRIES

class FeedReaderDbHelper(
    context: Context,
) : SQLiteOpenHelper(context, "alsong.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = SQL_CREATE_ENTRIES
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int,
    ) {
        db?.execSQL("DROP TABLE IF EXISTS notation")
        onCreate(db)
    }
}
