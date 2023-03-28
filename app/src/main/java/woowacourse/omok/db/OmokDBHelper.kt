package woowacourse.omok.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class OmokDBHelper(
    context: Context
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(PlayerContract.CREATE_TABLE)
        db?.execSQL(BoardContract.CREATE_TABLE)
    }

    override fun onOpen(db: SQLiteDatabase?) {
        super.onOpen(db)
        db?.execSQL("PRAGMA foreign_keys = ON;")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(PlayerContract.DROP_TABLE)
        db?.execSQL(BoardContract.DROP_TABLE)
        onCreate(db)
    }

    companion object {
        const val DATABASE_NAME = "omok.db"
        const val DATABASE_VERSION = 1
    }
}
