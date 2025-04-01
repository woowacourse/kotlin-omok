package woowacourse.omok.data.dao

import android.content.ContentValues
import android.database.Cursor
import woowacourse.omok.data.MovesTableContract
import woowacourse.omok.data.OmokDatabaseHelper
import woowacourse.omok.domain.board.CellState
import woowacourse.omok.domain.board.Point

class MovesDao(
    private val dbHelper: OmokDatabaseHelper,
) {
    fun saveMove(
        gameId: Int,
        move: Pair<Point, CellState>,
    ): Result<Unit> =
        dbHelper.safeDatabaseOperation { db ->
            val values =
                ContentValues().apply {
                    put(MovesTableContract.COLUMN_NAME_GAME_ID_FK, gameId)
                    put(MovesTableContract.COLUMN_NAME_X, move.first.x)
                    put(MovesTableContract.COLUMN_NAME_Y, move.first.y)
                    put(MovesTableContract.COLUMN_NAME_COLOR, move.second.toString())
                }
            val result = db.insert(MovesTableContract.TABLE_NAME, null, values)
            if (result == -1L) {
                throw Exception("착수 저장 실패")
            }
        }

    fun getMoves(gameId: Int): Result<List<Pair<Point, CellState>>> =
        dbHelper.safeDatabaseOperation { db ->
            val cursor =
                db.query(
                    MovesTableContract.TABLE_NAME,
                    arrayOf(
                        MovesTableContract.COLUMN_NAME_X,
                        MovesTableContract.COLUMN_NAME_Y,
                        MovesTableContract.COLUMN_NAME_COLOR,
                    ),
                    "${MovesTableContract.COLUMN_NAME_GAME_ID_FK} = ?",
                    arrayOf(gameId.toString()),
                    null,
                    null,
                    null,
                )

            val moves = mutableListOf<Pair<Point, CellState>>()
            cursor.use {
                while (it.moveToNext()) {
                    moves.add(cursorToMove(it))
                }
            }
            moves
        }

    private fun cursorToMove(cursor: Cursor): Pair<Point, CellState> {
        val x = cursor.getInt(0)
        val y = cursor.getInt(1)
        val color = cursor.getString(2)
        val boardCell = CellState.entries.find { it.toString() == color } ?: CellState.EMPTY
        return Point(x, y) to boardCell
    }
}
