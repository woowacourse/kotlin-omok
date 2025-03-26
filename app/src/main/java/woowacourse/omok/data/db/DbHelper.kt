package woowacourse.omok.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(
    context: Context,
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Board.db"
        const val DATABASE_NAME = "Omok.db"
    }
    fun createBoardTable(db: SQLiteDatabase) {
        db.execSQL(BoardContract.SQL_CREATE_ENTRIES)
    }
    fun deleteBoardTable(db: SQLiteDatabase) {
        db.execSQL(BoardContract.SQL_DELETE_ENTRIES)
    }

    override fun onCreate(db: SQLiteDatabase) {
        createBoardTable(db)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int,
    ) {
        db.execSQL(BoardContract.SQL_DELETE_ENTRIES)
        deleteBoardTable(db)
        onCreate(db)
    }

    override fun onDowngrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int,
    ) {
        onUpgrade(db, oldVersion, newVersion)
    }
}
