package woowacourse.omok.data

import android.content.ContentValues
import android.database.Cursor
import android.util.Log
import omok.model.stone.Stone
import omok.model.stone.StoneColor
import omok.model.stone.position.Col
import omok.model.stone.position.Position
import omok.model.stone.position.Row

class OmokDao {
    private lateinit var dbHelper: DbHelper

    fun deleteDatabase() {
        val db = dbHelper.writableDatabase
        db.delete(OmokContract.TABLE_NAME, null, null)
        db.close()
    }

    fun insertOmok(row: Int, col: Int, stoneColor: String) {
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(OmokContract.COLUMN_ROW_POSITION, row)
            put(OmokContract.COLUMN_COL_POSITION, col)
            put(OmokContract.COLUMN_STONE_COLOR, stoneColor)
        }

        val newRowId = db.insert(OmokContract.TABLE_NAME, null, values)
        if (newRowId == -1L) {
            Log.e("MainActivity", "insert failed")
        } else {
            Log.d("MainActivity", "insert success: $newRowId")
        }
        db.close()
    }

    fun hasOmokData(): Boolean {
        val db = dbHelper.readableDatabase
        val query = "SELECT EXISTS (SELECT 1 FROM ${OmokContract.TABLE_NAME} LIMIT 1)"
        val cursor = db.rawQuery(query, null)

        var exists = false
        if (cursor.moveToFirst()) {
            exists = cursor.getInt(0) == 1
        }

        cursor.close()
        db.close()
        return exists
    }

    fun getAllStones(): List<Stone> {
        val dbReader = dbHelper.readableDatabase
        val result = mutableListOf<Stone>()

        val cursor: Cursor = dbReader.query(
            OmokContract.TABLE_NAME,
            arrayOf(
                OmokContract.COLUMN_ROW_POSITION,
                OmokContract.COLUMN_COL_POSITION,
                OmokContract.COLUMN_STONE_COLOR
            ),
            null,
            null,
            null,
            null,
            null
        )

        with(cursor) {
            while (moveToNext()) {
                val row = getInt(getColumnIndexOrThrow(OmokContract.COLUMN_ROW_POSITION))
                val col = getInt(getColumnIndexOrThrow(OmokContract.COLUMN_COL_POSITION))
                val stoneColor = getString(getColumnIndexOrThrow(OmokContract.COLUMN_STONE_COLOR))
                val color = when (stoneColor) {
                    "BLACK" -> StoneColor.BLACK
                    "WHITE" -> StoneColor.WHITE
                    else -> continue
                }
                result.add(Stone(Position(Row(row), Col(col)), color))
            }
        }
        cursor.close()
        return result
    }
}