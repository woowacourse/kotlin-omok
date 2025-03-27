package woowacourse.omok.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(
    context: Context,
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    private fun createGameTable(db: SQLiteDatabase) {
        db.execSQL(GameContract.SQL_CREATE_ENTRIES)
    }

    private fun createBoardTable(db: SQLiteDatabase) {
        db.execSQL(BoardContract.SQL_CREATE_ENTRIES)
    }

    private fun deleteGameTable(db: SQLiteDatabase) {
        db.execSQL(GameContract.SQL_DELETE_ENTRIES)
    }

    private fun deleteBoardTable(db: SQLiteDatabase) {
        db.execSQL(BoardContract.SQL_DELETE_ENTRIES)
    }

    override fun onCreate(db: SQLiteDatabase) {
        createGameTable(db)
        createBoardTable(db)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int,
    ) {
        deleteBoardTable(db)
        deleteGameTable(db)
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
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Omok.db"
    }
}
