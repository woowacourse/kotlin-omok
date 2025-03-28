package woowacourse.omok.data.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import woowacourse.omok.domain.StoneColor
import woowacourse.omok.domain.grid.Column
import woowacourse.omok.domain.grid.OmokPoint
import woowacourse.omok.domain.grid.Point
import woowacourse.omok.domain.grid.Row

class DbProvider(context: Context) {
    private val dbHelper: DbHelper = DbHelper(context)

    fun initGame(): List<OmokPoint> {
        dbHelper.writableDatabase.execSQL(BoardContract.SQL_CREATE_ENTRIES)

        return queryBoardByColor()
    }

    private fun queryBoardByColor(): List<OmokPoint> {
        val dbReader = dbHelper.readableDatabase
        val result = mutableListOf<OmokPoint>()

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
                result.add(OmokPoint(Point(Row(row), Column(col)), parsingStoneColor(stoneColor)))
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

    fun insertStone(omokPoint: OmokPoint) {
        val db = dbHelper.writableDatabase
        val point = omokPoint.point

        val values =
            ContentValues().apply {
                put(BoardContract.COLUMN_NAME_ROW, point.row.value)
                put(BoardContract.COLUMN_NAME_COL, point.col.value)
                put(BoardContract.COLUMN_NAME_STONE_COLOR, omokPoint.stoneColor.toString())
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
