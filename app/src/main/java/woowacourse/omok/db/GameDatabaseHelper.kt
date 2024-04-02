package woowacourse.omok.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.omok.model.OmokGameState

class GameDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_GAME_STATE_TABLE = "CREATE TABLE $TABLE_GAME_STATE($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_STATE TEXT)"
        db.execSQL(CREATE_GAME_STATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_GAME_STATE")
        onCreate(db)
    }

    fun saveGameState(omokGameState: OmokGameState): Boolean {
        val values = ContentValues().apply {
            put(COLUMN_STATE, omokGameState.serialize())
        }
        val result = writableDatabase.insert(TABLE_GAME_STATE, null, values)

        return result != -1L
    }


    fun clearGameState() {
        writableDatabase.delete(TABLE_GAME_STATE, null, null)
    }

    fun loadGameState(): String {
        val db = readableDatabase
        val cursor = db.query(TABLE_GAME_STATE, arrayOf(COLUMN_STATE), null, null, null, null, "$COLUMN_ID DESC", "1")
        var state = ""
        val columnIndex = cursor.getColumnIndex(COLUMN_STATE)
        if (columnIndex != -1 && cursor.moveToFirst()) {
            state = cursor.getString(columnIndex)
        }
        cursor.close()
        return state
    }

    companion object {
        const val DATABASE_NAME = "omok.db"
        const val DATABASE_VERSION = 1
        const val TABLE_GAME_STATE = "gameState"
        const val COLUMN_ID = "id"
        const val COLUMN_STATE = "state"
    }

}

