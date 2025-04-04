package woowacourse.omok.data.db

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import woowacourse.omok.domain.StoneColor
import woowacourse.omok.domain.grid.Column
import woowacourse.omok.domain.grid.Point
import woowacourse.omok.domain.grid.Row
import woowacourse.omok.domain.grid.Stone

class DbProvider(private val dbHelper: SQLiteOpenHelper) {
    fun createTable() {
        dbHelper.writableDatabase.execSQL(BoardContract.SQL_CREATE_ENTRIES)
    }

    fun readAll(): List<Stone> {
        val dbReader = dbHelper.readableDatabase
        val result = mutableListOf<Stone>()

        val cursor: Cursor =
            dbReader.query(
                BoardContract.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null,
            )

        with(cursor) {
            while (moveToNext()) {
                val row = getInt(getColumnIndexOrThrow(BoardContract.COLUMN_NAME_ROW))
                val col = getInt(getColumnIndexOrThrow(BoardContract.COLUMN_NAME_COL))
                val stoneColor = getString(getColumnIndexOrThrow(BoardContract.COLUMN_NAME_STONE_COLOR))
                result.add(Stone(Point(Row(row), Column(col)), parsingStoneColor(stoneColor)))
            }
        }
        cursor.close()
        return result
    }

    private fun parsingStoneColor(color: String): StoneColor {
        return if (color == StoneColor.BLACK.toString()) {
            StoneColor.BLACK
        } else {
            StoneColor.WHITE
        }
    }

    fun insertStone(stone: Stone) {
        val db = dbHelper.writableDatabase
        val point = stone.point

        val values =
            ContentValues().apply {
                put(BoardContract.COLUMN_NAME_ROW, point.row.value)
                put(BoardContract.COLUMN_NAME_COL, point.col.value)
                put(BoardContract.COLUMN_NAME_STONE_COLOR, stone.stoneColor.toString())
            }

        val newRowId = db.insert(BoardContract.TABLE_NAME, null, values)
        if (newRowId == -1L) {
            Log.e("MainActivity", "insert failed")
        } else {
            Log.d("MainActivity", "insert success: $newRowId")
        }
        db.close()
    }

    fun dropTable() {
        dbHelper.writableDatabase.execSQL(BoardContract.SQL_DELETE_ENTRIES)
    }

    fun closeDB() {
        dbHelper.close()
    }
}
