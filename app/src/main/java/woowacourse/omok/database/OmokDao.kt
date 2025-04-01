package woowacourse.omok.database

import android.content.ContentValues
import android.database.Cursor
import woowacourse.omok.model.stone.Stone
import woowacourse.omok.model.stone.StoneColor
import woowacourse.omok.model.stone.Stones

class OmokDao(
    private val dbHelper: DbHelper,
) {
    fun insertStone(stone: Stone) {
        val db = dbHelper.writableDatabase

        val values = ContentValues()
        values.put("color", stone.color.name)
        values.put("row", stone.point.row)
        values.put("column", stone.point.col)

        db.insert(OmokContract.OmokStone.TABLE_NAME, null, values)
    }

    fun readStones(): Stones {
        val dbReader = dbHelper.readableDatabase

        val result = mutableListOf<Stone>()

        val cursor: Cursor =
            dbReader.rawQuery("SELECT * FROM ${OmokContract.OmokStone.TABLE_NAME}", arrayOf())
        with(cursor) {
            while (moveToNext()) {
                val color =
                    StoneColor.valueOf(getString(getColumnIndexOrThrow(OmokContract.OmokStone.COLUMN_NAME_COLOR)))
                val row = getInt(getColumnIndexOrThrow(OmokContract.OmokStone.COLUMN_NAME_ROW))
                val col = getInt(getColumnIndexOrThrow(OmokContract.OmokStone.COLUMN_NAME_COLUMN))
                result.add(Stone(row, col, color))
            }
        }
        cursor.close()
        return Stones(result.toSet(), result.lastOrNull())
    }

    fun deleteStones() {
        dbHelper.writableDatabase.delete(OmokContract.OmokStone.TABLE_NAME, null, null)
    }

    fun close() {
        dbHelper.close()
    }
}
