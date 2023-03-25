package woowacourse.omok

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import model.domain.tools.Board
import model.domain.tools.Dot
import model.domain.tools.Location
import model.domain.tools.Stone
import woowacourse.omok.OmokViewUtil.getStoneColor
import woowacourse.omok.model.data.OmokContract.TABLE_COLUMN_INDEX
import woowacourse.omok.model.data.OmokContract.TABLE_COLUMN_STONE_COLOR
import woowacourse.omok.model.data.OmokContract.TABLE_NAME
import woowacourse.omok.model.data.OmokDbHelper

class OmokDbAdapter(db: OmokDbHelper) {

    private val wdb: SQLiteDatabase = db.writableDatabase
    private val cursor = wdb.query(
        TABLE_NAME,
        arrayOf(TABLE_COLUMN_INDEX, TABLE_COLUMN_STONE_COLOR),
        null,
        null,
        null,
        null,
        null
    )

    fun load(board: Board) {

        while (cursor.moveToNext()) {
            val index = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_COLUMN_INDEX))
            val stone =
                cursor.getString(cursor.getColumnIndexOrThrow(TABLE_COLUMN_STONE_COLOR))

            val dot = Dot.from(index, BOARD_SIZE)
            getStoneColor(stone)?.let { board.placeStone(Location(dot.row, dot.col), it) }
        }
    }

    fun nextStone(): Stone {
        if (cursor.count.isOdd()) {
            cursor.close()
            return Stone.WHITE
        }
        return Stone.BLACK
    }

    fun saveStone(index: Int, stone: Stone) {
        val values = ContentValues()
        values.put(TABLE_COLUMN_INDEX, index)
        values.put(TABLE_COLUMN_STONE_COLOR, OmokViewUtil.getStoneMessage(stone))

        wdb.insert(TABLE_NAME, null, values)
    }

    fun deleteStones() {
        wdb.delete(TABLE_NAME, null, null)
    }

    private fun Int.isOdd() = this % 2 != 0
}
