package woowacourse.omok.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val createGameBoardTable = """
    CREATE TABLE ${GameBoardContract.GameBoardEntry.TABLE_NAME} (
        ${GameBoardContract.GameBoardEntry.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
        ${GameBoardContract.GameBoardEntry.COLUMN_ROW_INDEX} INTEGER,
        ${GameBoardContract.GameBoardEntry.COLUMN_COLUMN_INDEX} INTEGER,
        ${GameBoardContract.GameBoardEntry.COLUMN_STONE_TYPE} INTEGER,
        UNIQUE (${GameBoardContract.GameBoardEntry.COLUMN_ROW_INDEX}, ${GameBoardContract.GameBoardEntry.COLUMN_COLUMN_INDEX})
    )""".trimIndent()
        db.execSQL(createGameBoardTable)

        val createGameStatusTable = """
    CREATE TABLE ${GameBoardContract.GameStatusEntry.TABLE_NAME} (
        ${GameBoardContract.GameStatusEntry.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
        ${GameBoardContract.GameStatusEntry.COLUMN_CURRENT_STONE} INTEGER
    )
""".trimIndent()
        db.execSQL(createGameStatusTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
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
