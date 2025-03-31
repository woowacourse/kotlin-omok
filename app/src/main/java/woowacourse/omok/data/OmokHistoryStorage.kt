package woowacourse.omok.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import woowacourse.omok.data.OmokDbHelper.Companion.COLUMN_NAME_POSITION_COLUMN
import woowacourse.omok.data.OmokDbHelper.Companion.COLUMN_NAME_POSITION_ROW
import woowacourse.omok.data.OmokDbHelper.Companion.COLUMN_NAME_TURN
import woowacourse.omok.data.OmokDbHelper.Companion.TABLE_NAME

interface OmokHistoryStorage {
    fun fetch(): List<History>

    fun add(history: History)

    fun clear()

    fun close()
}

class DefaultOmokHistoryHistoryStorage(
    context: Context,
) : OmokHistoryStorage {
    private val omokDbHelper: SQLiteOpenHelper = OmokDbHelper(context)

    override fun fetch(): List<History> {
        val dbReader = omokDbHelper.readableDatabase
        val result = mutableListOf<History>()

        val cursor: Cursor =
            dbReader.query(
                TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null,
            )

        with(cursor) {
            while (moveToNext()) {
                val turn: String = getString(getColumnIndexOrThrow(COLUMN_NAME_TURN))
                val row = getInt(getColumnIndexOrThrow(COLUMN_NAME_POSITION_ROW))
                val column = getInt(getColumnIndexOrThrow(COLUMN_NAME_POSITION_COLUMN))
                result.add(History(turn, row, column))
            }
        }
        cursor.close()
        return result
    }

    override fun add(history: History) {
        val db = omokDbHelper.writableDatabase

        val values =
            ContentValues().apply {
                put(COLUMN_NAME_TURN, history.turn)
                put(COLUMN_NAME_POSITION_ROW, history.row)
                put(COLUMN_NAME_POSITION_COLUMN, history.column)
            }

        val newRowId = db.insert(TABLE_NAME, null, values)
        if (newRowId == -1L) {
            Log.e(this::class.simpleName, "insert failed")
        } else {
            Log.d(this::class.simpleName, "insert success: $newRowId")
        }
        db.close()
    }

    override fun clear() {
        omokDbHelper.writableDatabase.execSQL("DELETE FROM $TABLE_NAME")
    }

    override fun close() {
        omokDbHelper.close()
    }
}
