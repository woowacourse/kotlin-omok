package woowacourse.omok.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class OmokDbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(OmokContract.SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int,
    ) {
        db.execSQL(OmokContract.SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int,
    ) {
        onUpgrade(db, oldVersion, newVersion)
    }

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "Omok.db"
    }
}
