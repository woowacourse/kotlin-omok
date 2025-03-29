package woowacourse.omok.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE $OMOK_BOARD (
                x INTEGER,
                y INTEGER,
                state TEXT
            )
            """.trimIndent(),
        )
        db.execSQL(
            """
            CREATE TABLE $CURRENT_TURN (
               id INTEGER PRIMARY KEY AUTOINCREMENT,
               color TEXT
            )
            """.trimIndent(),
        )
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int,
    ) {
        db.execSQL("DROP TABLE IF EXISTS $OMOK_BOARD")
        db.execSQL("DROP TABLE IF EXISTS $CURRENT_TURN")
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "omok.db"
        private const val DATABASE_VERSION = 2
        const val OMOK_BOARD = "omok_board"
        const val CURRENT_TURN = "current_turn"
    }
}
