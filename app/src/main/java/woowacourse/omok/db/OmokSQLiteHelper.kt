package woowacourse.omok.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.omok.db.OmokContract.DATABASE_NAME
import woowacourse.omok.db.OmokContract.DATABASE_VERSION

class OmokSQLiteHelper(context: Context) :
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
}
