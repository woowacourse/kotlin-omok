package woowacourse.omok.domain.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log

class OmokRepository(context: Context) {
    private val dbHelper = DbHelper(context)

    fun insertStone(
        color: String,
        row: Int,
        column: Int,
    ) {
        val db = dbHelper.writableDatabase

        val values =
            ContentValues().apply {
                put(OmokContract.STONE_COLOR, color)
                put(OmokContract.POSITION_ROW, row)
                put(OmokContract.POSITION_COLUMN, column)
            }

        val newRowId = db.insert(OmokContract.TABLE_NAME, null, values)
        if (newRowId == -1L) {
            Log.e("MainActivity", "insert failed")
        } else {
            Log.d("MainActivity", "insert success: $newRowId")
        }
        db.close()
    }

    fun queryStoneAt(
        row: Int,
        column: Int,
    ): String? {
        val dbReader = dbHelper.readableDatabase
        var result: String? = null

        val cursor: Cursor =
            dbReader.query(
                OmokContract.TABLE_NAME,
                arrayOf(OmokContract.STONE_COLOR),
                "${OmokContract.POSITION_ROW} = ? AND ${OmokContract.POSITION_COLUMN} = ?",
                arrayOf(row.toString(), column.toString()),
                null,
                null,
                null,
            )

        if (cursor.moveToFirst()) {
            result = cursor.getString(cursor.getColumnIndexOrThrow(OmokContract.STONE_COLOR))
        }
        cursor.close()
        return result
    }

    fun resetDatabase() {
        val db = dbHelper.writableDatabase
        db.delete(OmokContract.TABLE_NAME, null, null)
        db.close()
    }

    fun close() {
        dbHelper.close()
    }
}
