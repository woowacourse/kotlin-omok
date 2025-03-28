package woowacourse.omok.domain.repository

import android.content.ContentValues
import android.content.Context
import woowacourse.omok.data.DbHelper
import woowacourse.omok.data.OmokContract
import woowacourse.omok.domain.model.omokboard.IntersectionState
import woowacourse.omok.domain.model.omokboard.OmokBoard
import woowacourse.omok.domain.model.omokboard.Position
import woowacourse.omok.domain.model.player.StoneColor

class OmokRepository(
    private val context: Context,
) {
    fun saveLastTurn(lastTurn: StoneColor) {
        val db = DbHelper(context).writableDatabase
        db.delete(OmokContract.TABLE_GAME, null, null)

        val values =
            ContentValues().apply {
                put(OmokContract.COLUMN_LAST_TURN, lastTurn.name)
            }

        db.insert(OmokContract.TABLE_GAME, null, values)
        db.close()
    }

    fun saveBoard(board: OmokBoard) {
        val db = DbHelper(context).writableDatabase
        db.delete(OmokContract.TABLE_BOARD, null, null)

        board.snapshot.forEach { (pos, state) ->
            val values =
                ContentValues().apply {
                    put(OmokContract.COLUMN_POSITION_ROW, pos.row)
                    put(OmokContract.COLUMN_POSITION_COL, pos.column)
                    put(OmokContract.COLUMN_POSITION_STATE, state.name)
                }
            db.insert(OmokContract.TABLE_BOARD, null, values)
        }

        db.close()
    }

    fun loadLastTurn(): StoneColor? {
        val db = DbHelper(context).readableDatabase
        val cursor =
            db.query(
                OmokContract.TABLE_GAME,
                arrayOf(OmokContract.COLUMN_LAST_TURN),
                null,
                null,
                null,
                null,
                null,
                "1",
            )

        val lastTurn =
            if (cursor.moveToFirst()) {
                cursor.getString(cursor.getColumnIndexOrThrow(OmokContract.COLUMN_LAST_TURN))
            } else {
                null
            }

        cursor.close()
        db.close()
        return lastTurn?.let { StoneColor.valueOf(it) } ?: return null
    }

    fun loadBoard(): OmokBoard? {
        val db = DbHelper(context).readableDatabase
        val cursor =
            db.query(
                OmokContract.TABLE_BOARD,
                arrayOf(
                    OmokContract.COLUMN_POSITION_ROW,
                    OmokContract.COLUMN_POSITION_COL,
                    OmokContract.COLUMN_POSITION_STATE,
                ),
                null,
                null,
                null,
                null,
                null,
            )

        val board = mutableMapOf<Position, IntersectionState>()
        while (cursor.moveToNext()) {
            val row = cursor.getInt(cursor.getColumnIndexOrThrow(OmokContract.COLUMN_POSITION_ROW))
            val col = cursor.getInt(cursor.getColumnIndexOrThrow(OmokContract.COLUMN_POSITION_COL))
            val state =
                IntersectionState.valueOf(
                    cursor.getString(cursor.getColumnIndexOrThrow(OmokContract.COLUMN_POSITION_STATE)),
                )
            board[Position(row, col)] = state
        }

        cursor.close()
        db.close()

        return if (board.isEmpty()) null else OmokBoard(board)
    }

    fun clearGameData() {
        val db = DbHelper(context).writableDatabase
        db.delete(OmokContract.TABLE_GAME, null, null)
        db.delete(OmokContract.TABLE_BOARD, null, null)
        db.close()
    }
}
