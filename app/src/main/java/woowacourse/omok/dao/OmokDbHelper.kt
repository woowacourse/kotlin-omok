package woowacourse.omok.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class OmokDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
        CREATE TABLE IF NOT EXISTS $TABLE_NAME (
            $ID_COLUMN INTEGER PRIMARY KEY AUTOINCREMENT,
            $NICKNAME_COLUMN VARCHAR(10) NOT NULL UNIQUE,
             $BOARD_COLUMN TEXT NOT NULL
        )
    """
        db.execSQL(createTable)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int,
    ) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME ")
        onCreate(db)
    }

    companion object {
        const val TABLE_NAME = "omok_board"
        const val DATABASE_NAME = "omok.db"
        const val NICKNAME_COLUMN = "nickname"
        const val ID_COLUMN = "id"
        const val BOARD_COLUMN = "board"
        const val DATABASE_VERSION = 1
    }
}
