package woowacourse.omok.db

import android.content.Context
import android.database.Cursor
import domain.stone.Color
import domain.stone.Position
import domain.stone.Stone
import woowacourse.omok.db.vo.StoneVO

class StoneTableAdapter(
    context: Context,
    private val roomNumber: Int
) {
    private val wDb = StonesHelper(context).writableDatabase

    fun insertStone(stone: Stone) {
        wDb.execSQL(
            "INSERT INTO ${StoneConstract.TABLE_NAME} (${StoneConstract.TABLE_COLUMN_ROOM_NUMBER}, ${StoneConstract.TABLE_COLUMN_COLOR}, ${StoneConstract.TABLE_COLUMN_X}, ${StoneConstract.TABLE_COLUMN_Y})" +
                "VALUES ($roomNumber, ${stone.color.ordinal}, ${stone.position.column.ordinal}, ${stone.position.row.ordinal});"
        )
    }

    fun getStoneVOs(): List<StoneVO> {
        val cursor = getAllStonesCursor()
        val result = mutableListOf<StoneVO>().apply {
            while (cursor.moveToNext()) {
                add(convertCursorToStoneVO(cursor))
            }
        }
        cursor.close()
        return result
    }

    private fun getAllStonesCursor(): Cursor {
        return wDb.query(
            StoneConstract.TABLE_NAME,
            StoneConstract.allColumn(),
            "${StoneConstract.TABLE_COLUMN_ROOM_NUMBER} = ?",
            arrayOf(roomNumber.toString()),
            null,
            null,
            "${StoneConstract.TABLE_COLUMN_ID} DESC"
        )
    }

    private fun convertCursorToStoneVO(cursor: Cursor): StoneVO = StoneVO(
        cursor.getInt(cursor.getColumnIndexOrThrow(StoneConstract.TABLE_COLUMN_ID)),
        Color.values()[cursor.getInt(cursor.getColumnIndexOrThrow(StoneConstract.TABLE_COLUMN_COLOR))],
        Position(
            cursor.getInt(cursor.getColumnIndexOrThrow(StoneConstract.TABLE_COLUMN_X)) + 1,
            cursor.getInt(cursor.getColumnIndexOrThrow(StoneConstract.TABLE_COLUMN_Y)) + 1
        ),
        cursor.getInt(cursor.getColumnIndexOrThrow(StoneConstract.TABLE_COLUMN_ROOM_NUMBER))
    )

    fun deleteAll() {
        wDb.execSQL(
            "DELETE FROM ${StoneConstract.TABLE_NAME} " +
                "WHERE ${StoneConstract.TABLE_COLUMN_ROOM_NUMBER} = '$roomNumber';"
        )
    }

    fun close() {
        wDb.close()
    }
}
