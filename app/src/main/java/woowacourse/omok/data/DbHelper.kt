package woowacourse.omok.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.omok.data.StateContract.DATABASE_NAME
import woowacourse.omok.data.StateContract.DATABASE_VERSION
import woowacourse.omok.data.StateContract.SQL_CREATE_BLACK_STONES
import woowacourse.omok.data.StateContract.SQL_CREATE_STATE
import woowacourse.omok.data.StateContract.SQL_CREATE_WHITE_STONES
import woowacourse.omok.data.StateContract.SQL_DELETE_BLACK_STONES
import woowacourse.omok.data.StateContract.SQL_DELETE_STATE
import woowacourse.omok.data.StateContract.SQL_DELETE_WHITE_STONES

class DbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_STATE)
        db.execSQL(SQL_CREATE_BLACK_STONES)
        db.execSQL(SQL_CREATE_WHITE_STONES)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int,
    ) {
        db.execSQL(SQL_DELETE_STATE)
        db.execSQL(SQL_DELETE_BLACK_STONES)
        db.execSQL(SQL_DELETE_WHITE_STONES)
        onCreate(db)
    }
}
