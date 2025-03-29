package woowacourse.omok.database

import android.content.ContentValues
import android.database.Cursor
import android.util.Log
import woowacourse.omok.domain.Position
import woowacourse.omok.domain.Stone
import woowacourse.omok.domain.StoneType

class DatabaseStoneDAO(private val dbHelper: DbHelper) : StoneDAO {
    override fun insertStone(
        row: Int,
        column: Int,
        color: String,
    ) {
        val db = dbHelper.writableDatabase

        val values =
            ContentValues().apply {
                put(BoardContract.COLUMN_NAME_COLOR, color)
                put(BoardContract.COLUMN_NAME_POSITION_ROW, row)
                put(BoardContract.COLUMN_NAME_POSITION_COLUMN, column)
            }

        val newRowId = db.insert(BoardContract.TABLE_NAME_BOARD, null, values)
        if (newRowId == -1L) {
            Log.e("DatabaseStoneDAO", "insert failed")
        } else {
            Log.d("DatabaseStoneDAO", "insert success: $newRowId")
        }
        db.close()
    }

    override fun queryStones(): List<Stone> {
        val dbReader = dbHelper.readableDatabase
        val result = mutableListOf<Stone>()

        val cursorCheck = dbReader.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='board'", null)
        if (cursorCheck.count == 0) {
            cursorCheck.close()
            return emptyList()
        }
        cursorCheck.close()

        val cursor: Cursor = dbReader.rawQuery("SELECT * FROM board", null)

        with(cursor) {
            while (moveToNext()) {
                val color = getString(getColumnIndexOrThrow(BoardContract.COLUMN_NAME_COLOR))
                val row = getInt(getColumnIndexOrThrow(BoardContract.COLUMN_NAME_POSITION_ROW))
                val column = getInt(getColumnIndexOrThrow(BoardContract.COLUMN_NAME_POSITION_COLUMN))
                val stoneColor = if (color == "black") StoneType.BLACK else StoneType.WHITE
                result.add(Stone(Position(row, column), stoneColor))
            }
        }
        cursor.close()
        return result
    }

    override fun clear() {
        val db = dbHelper.writableDatabase
        db.execSQL(BoardContract.SQL_DELETE_BOARD_ENTRIES)
        db.execSQL(BoardContract.SQL_CREATE_BOARD_ENTERIES)
        db.close()
    }
}
