package woowacourse.omok.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class OmokDbHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE $TABLE_NAME (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                x INTEGER NOT NULL,
                y INTEGER NOT NULL,
                stone TEXT NOT NULL
            )
            """.trimIndent()
        )

        db.execSQL(
            """
        CREATE TABLE turn (
            id INTEGER PRIMARY KEY,
            current_turn TEXT NOT NULL
        );
        """.trimIndent()
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        db.execSQL("DROP TABLE IF EXISTS turn")
        onCreate(db)
    }

    companion object {
        const val DB_NAME = "OmokGame.db"
        const val DB_VERSION = 2
        const val TABLE_NAME = "stones"
    }
}