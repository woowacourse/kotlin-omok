package woowacourse.omok

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import model.domain.OmokGame.Companion.BOARD_SIZE
import model.domain.tools.Board
import model.domain.tools.Dot
import model.domain.tools.Location
import model.domain.tools.Stone
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
        values.put(TABLE_COLUMN_STONE_COLOR, getStoneMessage(stone))

        wdb.insert(TABLE_NAME, null, values)
    }

    fun deleteStones() {
        wdb.delete(TABLE_NAME, null, null)
    }

    private fun getStoneColor(stoneMessage: String) =
        when (stoneMessage) {
            BLACK_STONE_MESSAGE -> Stone.BLACK
            WHITE_STONE_MESSAGE -> Stone.WHITE
            EMPTY_STONE_MESSAGE -> Stone.EMPTY
            else -> null
        }

    private fun getStoneMessage(stone: Stone) =
        when (stone) {
            Stone.BLACK -> BLACK_STONE_MESSAGE
            Stone.WHITE -> WHITE_STONE_MESSAGE
            Stone.EMPTY -> EMPTY_STONE_MESSAGE
        }

    private fun Int.isOdd() = this % 2 != 0

    companion object {
        const val BLACK_STONE_MESSAGE = "BLACK"
        const val WHITE_STONE_MESSAGE = "WHITE"
        const val EMPTY_STONE_MESSAGE = "EMPTY"
    }
}
