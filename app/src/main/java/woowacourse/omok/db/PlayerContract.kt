package woowacourse.omok.db

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

object PlayerContract {
    const val TABLE_NAME = "player"
    const val COLUMN_NAME_NICKNAME = "nickname"

    const val CREATE_TABLE = "CREATE TABLE $TABLE_NAME ($COLUMN_NAME_NICKNAME VARCHAR(4) PRIMARY KEY)"
    const val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"

    fun createRecord(db: SQLiteDatabase, nickname: String) {
        val values = ContentValues()
        values.put(COLUMN_NAME_NICKNAME, nickname)

        db.insert(TABLE_NAME, null, values)
    }

    fun readRecord(db: SQLiteDatabase, nickname: String): Cursor {
        return db.query(
            TABLE_NAME,
            arrayOf(COLUMN_NAME_NICKNAME),
            "$COLUMN_NAME_NICKNAME = ?",
            arrayOf(nickname),
            null,
            null,
            null
        )
    }

    fun deleteRecord(db: SQLiteDatabase, nickname: String) {
        db.delete(TABLE_NAME, "$COLUMN_NAME_NICKNAME = ?", arrayOf(nickname))
    }
}
