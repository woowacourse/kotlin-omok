package woowacourse.omok.data

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.omok.domain.position.Col
import woowacourse.omok.domain.position.Position
import woowacourse.omok.domain.position.Row
import woowacourse.omok.domain.stone.Stone
import woowacourse.omok.domain.stone.StoneColor

class StoneDao(
    private val dbHelper: SQLiteOpenHelper,
) {
    private val readableDatabase: SQLiteDatabase by lazy { dbHelper.readableDatabase }
    private val writableDatabase: SQLiteDatabase by lazy { dbHelper.writableDatabase }

    fun findAllStone(): List<Stone> {
        readableDatabase.rawQuery(OmokContract.SQL_FETCH_STONES, arrayOf()).use { cursor ->
            val stones = mutableListOf<Stone>()
            while (cursor.moveToNext()) {
                val stoneColorIndex = cursor.getColumnIndex(OmokContract.COLUMN_NAME_STONE_COLOR)
                val rowIndex = cursor.getColumnIndex(OmokContract.COLUMN_NAME_ROW)
                val colIndex = cursor.getColumnIndex(OmokContract.COLUMN_NAME_COL)

                val stoneColor: String = cursor.getString(stoneColorIndex)
                val row = cursor.getInt(rowIndex)
                val col = cursor.getInt(colIndex)
                stones.add(
                    Stone(Position(Row.from(row), Col.from(col)), stoneColor.toStoneColor()),
                )
            }
            return stones.toList()
        }
    }

    fun insert(stone: Stone) {
        val values =
            ContentValues().apply {
                put(OmokContract.COLUMN_NAME_STONE_COLOR, stone.color.name)
                put(OmokContract.COLUMN_NAME_ROW, stone.position.row.value)
                put(OmokContract.COLUMN_NAME_COL, stone.position.col.value)
            }
        writableDatabase.insert(OmokContract.TABLE_NAME, null, values)
    }

    fun deleteAll() {
        writableDatabase.delete(OmokContract.TABLE_NAME, null, null)
    }

    private fun String.toStoneColor(): StoneColor {
        return if (this == "BLACK") {
            StoneColor.BLACK
        } else {
            StoneColor.WHITE
        }
    }
}
