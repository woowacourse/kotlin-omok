package woowacourse.omok.db

import android.content.ContentValues
import android.content.Context

class BoardDaoImpl(context: Context) : BoardDao {
    override val dbHelper = DatabaseHelper(context)

    override fun insertStone(boardDto: BoardDto) {
        val db = dbHelper.writableDatabase
        val values =
            ContentValues().apply {
                put("x", boardDto.x)
                put("y", boardDto.y)
                put("state", boardDto.stoneColor)
            }
        db.insert(DatabaseHelper.TABLE_NAME, null, values)
        db.close()
    }

    override fun getAllStones(): List<BoardDto> {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT x, y, state FROM ${DatabaseHelper.TABLE_NAME}", null)
        val moves = mutableListOf<BoardDto>()

        while (cursor.moveToNext()) {
            val x = cursor.getInt(0)
            val y = cursor.getInt(1)
            val state = cursor.getString(2)
            moves.add(BoardDto(x, y, state))
        }
        cursor.close()
        db.close()
        return moves
    }

    override fun clearBoard() {
        val db = dbHelper.writableDatabase
        db.execSQL("DELETE FROM ${DatabaseHelper.TABLE_NAME}")
        db.close()
    }
}
