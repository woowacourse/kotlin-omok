package woowacourse.omok.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE GameBoard (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                rowIndex INTEGER,
                columnIndex INTEGER,
                stoneType INTEGER,
                UNIQUE (rowIndex, columnIndex)
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS GameBoard")
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "omok.db"
        private const val DATABASE_VERSION = 1
    }
}
