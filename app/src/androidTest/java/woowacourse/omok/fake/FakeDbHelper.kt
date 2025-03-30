package woowacourse.omok.fake

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import data.StateContract.DATABASE_NAME
import data.StateContract.DATABASE_VERSION
import data.StateContract.SQL_CREATE_BLACK_STONES
import data.StateContract.SQL_CREATE_STATE
import data.StateContract.SQL_CREATE_WHITE_STONES
import data.StateContract.SQL_DELETE_BLACK_STONES
import data.StateContract.SQL_DELETE_STATE
import data.StateContract.SQL_DELETE_WHITE_STONES

class FakeDbHelper(context: Context) :
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
