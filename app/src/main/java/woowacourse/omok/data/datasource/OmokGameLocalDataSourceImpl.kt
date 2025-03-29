package woowacourse.omok.data.datasource

import android.content.ContentValues
import android.content.Context
import androidx.core.database.sqlite.transaction
import woowacourse.omok.data.DbHelper
import woowacourse.omok.data.OmokContract
import woowacourse.omok.data.model.OmokGameInfoDto

class OmokGameLocalDataSourceImpl(
    private val context: Context,
) : OmokGameLocalDataSource {
    override fun save(omokGameInfoDto: OmokGameInfoDto) {
        val db = DbHelper(context).writableDatabase
        db.transaction {
            delete(OmokContract.TABLE_GAME, null, null)
            insert(
                OmokContract.TABLE_GAME,
                null,
                ContentValues().apply {
                    put(OmokContract.COLUMN_LAST_TURN, omokGameInfoDto.lastTurn)
                },
            )

            delete(OmokContract.TABLE_BOARD, null, null)
            omokGameInfoDto.board.forEach { (key, value) ->
                insert(
                    OmokContract.TABLE_BOARD,
                    null,
                    ContentValues().apply {
                        put(OmokContract.COLUMN_POSITION_ROW, key.first)
                        put(OmokContract.COLUMN_POSITION_COL, key.second)
                        put(OmokContract.COLUMN_POSITION_STATE, value)
                    },
                )
            }
        }
        db.close()
    }

    override fun load(): OmokGameInfoDto? {
        val db = DbHelper(context).readableDatabase
        val turnCursor =
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
            when (turnCursor.moveToFirst()) {
                true -> turnCursor.getString(turnCursor.getColumnIndexOrThrow(OmokContract.COLUMN_LAST_TURN))
                false -> null
            }
        turnCursor.close()

        val boardCursor =
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

        val board = mutableMapOf<Pair<Int, Int>, String>()
        while (boardCursor.moveToNext()) {
            val row = boardCursor.getInt(boardCursor.getColumnIndexOrThrow(OmokContract.COLUMN_POSITION_ROW))
            val col = boardCursor.getInt(boardCursor.getColumnIndexOrThrow(OmokContract.COLUMN_POSITION_COL))
            val state = boardCursor.getString(boardCursor.getColumnIndexOrThrow(OmokContract.COLUMN_POSITION_STATE))
            board[Pair(row, col)] = state
        }

        boardCursor.close()
        db.close()

        return when (lastTurn != null && board.isNotEmpty()) {
            true -> OmokGameInfoDto(lastTurn, board)
            false -> null
        }
    }

    override fun delete() {
        val db = DbHelper(context).writableDatabase
        db.transaction {
            delete(OmokContract.TABLE_GAME, null, null)
            delete(OmokContract.TABLE_BOARD, null, null)
        }
        db.close()
    }
}
