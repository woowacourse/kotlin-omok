package woowacourse.omok.fake

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.omok.db.OmokContract

class FakeOmokSQLiteHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(OmokContract.GameRecordEntry.SQL_CREATE_TABLE)
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        new: Int,
        old: Int,
    ) {
        db?.execSQL(OmokContract.GameRecordEntry.SQL_DELETE_TABLE)
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "FakeOmokGame.db"
        private const val DATABASE_VERSION = 1
    }
}
