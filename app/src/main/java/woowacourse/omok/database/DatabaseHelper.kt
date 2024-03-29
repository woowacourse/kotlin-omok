package woowacourse.omok.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val createGameBoardTable = """
        CREATE TABLE GameBoard (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            rowIndex INTEGER,
            columnIndex INTEGER,
            stoneType INTEGER,
            UNIQUE (rowIndex, columnIndex)
        )
    """.trimIndent()
        db.execSQL(createGameBoardTable)

        val createGameStatusTable = """
        CREATE TABLE GameStatus (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            currentStone INTEGER
        )
    """.trimIndent()
        db.execSQL(createGameStatusTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            // GameStatus 테이블 추가
            val createGameStatusTable = """
            CREATE TABLE GameStatus (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                currentStone INTEGER
            )
        """.trimIndent()
            db.execSQL(createGameStatusTable)
        }
        db.execSQL("DROP TABLE IF EXISTS GameBoard")
        db.execSQL("DROP TABLE IF EXISTS GameStatus")
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "omok.db"
        private const val DATABASE_VERSION = 2
    }
}
