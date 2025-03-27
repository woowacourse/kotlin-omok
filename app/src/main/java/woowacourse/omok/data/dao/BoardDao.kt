package woowacourse.omok.data.dao

import android.content.ContentValues
import android.database.Cursor
import woowacourse.omok.data.BoardTableContract
import woowacourse.omok.data.OmokDatabaseHelper
import woowacourse.omok.domain.board.Point
import woowacourse.omok.domain.board.StoneColor

class BoardDao(
    private val dbHelper: OmokDatabaseHelper,
) {
    fun saveMove(
        gameId: Int,
        move: Pair<Point, StoneColor>,
    ) {
        dbHelper.writableDatabase.use { db ->
            val values =
                ContentValues().apply {
                    put(BoardTableContract.COLUMN_NAME_GAME_ID_FK, gameId)
                    put(BoardTableContract.COLUMN_NAME_X, move.first.x)
                    put(BoardTableContract.COLUMN_NAME_Y, move.first.y)
                    put(BoardTableContract.COLUMN_NAME_COLOR, move.second.toString())
                }
            db.insert(BoardTableContract.TABLE_NAME, null, values)
        }
    }

    fun getMoves(gameId: Int): List<Pair<Point, StoneColor>> {
        val db = dbHelper.readableDatabase
        val cursor =
            db.query(
                BoardTableContract.TABLE_NAME,
                arrayOf(
                    BoardTableContract.COLUMN_NAME_X,
                    BoardTableContract.COLUMN_NAME_Y,
                    BoardTableContract.COLUMN_NAME_COLOR,
                ),
                "${BoardTableContract.COLUMN_NAME_GAME_ID_FK} = ?",
                arrayOf(gameId.toString()),
                null,
                null,
                null,
            )

        return cursor.use {
            val moves = mutableListOf<Pair<Point, StoneColor>>()
            while (it.moveToNext()) {
                moves.add(cursorToMove(it))
            }
            moves
        }
    }

    private fun cursorToMove(cursor: Cursor): Pair<Point, StoneColor> {
        val x = cursor.getInt(0)
        val y = cursor.getInt(1)
        val color = cursor.getString(2)
        val stoneColor = StoneColor.entries.find { it.toString() == color } ?: StoneColor.NONE
        return Point(x, y) to stoneColor
    }
}
