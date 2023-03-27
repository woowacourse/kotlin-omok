package woowacourse.omok.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class StoneDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "Stone.db"
        private const val SQL_CREATE_ENTRIES =
            "CREATE TABLE ${StoneEntry.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${StoneEntry.COLUMN_NAME_X} TEXT," +
                    "${StoneEntry.COLUMN_NAME_Y} INTEGER)"
        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${StoneEntry.TABLE_NAME}"
    }
}