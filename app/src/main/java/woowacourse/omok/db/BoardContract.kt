package woowacourse.omok.db

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

object BoardContract {
    const val TABLE_NAME = "board"
    const val COLUMN_NAME_NICKNAME = "nickname"
    const val COLUMN_NAME_POSITION = "position"
    const val COLUMN_NAME_STONE = "stone"

    const val CREATE_TABLE = "CREATE TABLE $TABLE_NAME " +
        "(" +
        "$COLUMN_NAME_NICKNAME VARCHAR(4)," +
        "$COLUMN_NAME_POSITION INT," +
        "$COLUMN_NAME_STONE INT NOT NULL," +
        "PRIMARY KEY ($COLUMN_NAME_NICKNAME, $COLUMN_NAME_POSITION)," +
        "FOREIGN KEY ($COLUMN_NAME_NICKNAME)" +
        "REFERENCES ${PlayerContract.TABLE_NAME} (${PlayerContract.COLUMN_NAME_NICKNAME})" +
        "ON DELETE CASCADE" +
        ");"
    const val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"

    fun createRecord(db: SQLiteDatabase, nickname: String, positionNumber: Int, stoneId: Int) {
        val values = ContentValues()
        values.put(COLUMN_NAME_NICKNAME, nickname)
        values.put(COLUMN_NAME_POSITION, positionNumber)
        values.put(COLUMN_NAME_STONE, stoneId)

        db.insert(TABLE_NAME, null, values)
    }

    fun readRecords(db: SQLiteDatabase, nickname: String): Cursor {
        return db.query(
            TABLE_NAME,
            arrayOf(COLUMN_NAME_POSITION, COLUMN_NAME_STONE),
            "$COLUMN_NAME_NICKNAME = ?",
            arrayOf(nickname),
            null,
            null,
            "$COLUMN_NAME_POSITION ASC"
        )
    }
}
