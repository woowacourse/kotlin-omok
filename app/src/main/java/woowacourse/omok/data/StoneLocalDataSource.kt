package woowacourse.omok.data

import android.database.sqlite.SQLiteOpenHelper
import woowacourse.omok.domain.position.Col
import woowacourse.omok.domain.position.Position
import woowacourse.omok.domain.position.Row
import woowacourse.omok.domain.stone.Stone
import woowacourse.omok.domain.stone.StoneColor

class StoneLocalDataSource(
    private val dbHelper: SQLiteOpenHelper,
) : StoneDataSource {
    override fun insert(stoneDao: StoneDao) {
        val db = dbHelper.writableDatabase
        val data =
            arrayOf(
                stoneDao.stoneColor,
                stoneDao.row.value,
                stoneDao.col.value,
            )
        db.execSQL(OmokContract.SQL_INSERT_STONE, data)
    }

    override fun fetchStoneByPosition(position: Position): Stone? {
        val db = dbHelper.readableDatabase
        val data =
            arrayOf(
                position.row.value.toString(),
                position.col.value.toString(),
            )
        val cursor = db.rawQuery(OmokContract.SQL_FETCH_STONE_BY_POSITION, data)

        if (cursor.moveToNext()) {
            val stoneColorIndex = cursor.getColumnIndex(OmokContract.COLUMN_NAME_STONE_COLOR)
            val rowIndex = cursor.getColumnIndex(OmokContract.COLUMN_NAME_ROW)
            val colIndex = cursor.getColumnIndex(OmokContract.COLUMN_NAME_COL)

            val stoneColorValue = cursor.getString(stoneColorIndex)
            val rowValue = cursor.getInt(rowIndex)
            val colValue = cursor.getInt(colIndex)
            return Stone(
                position = Position(Row.from(rowValue), Col.from(colValue)),
                color = stoneColorValue.toStoneColor(),
            )
        }
        return null
    }

    private fun String.toStoneColor(): StoneColor {
        return if (this == StoneColor.BLACK.name) {
            StoneColor.BLACK
        } else {
            StoneColor.WHITE
        }
    }
}
