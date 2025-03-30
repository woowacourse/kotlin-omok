package woowacourse.omok.data.datasource

import android.content.ContentValues
import android.content.Context
import androidx.core.database.sqlite.transaction
import woowacourse.omok.data.db.DbHelper
import woowacourse.omok.data.db.OmokContract
import woowacourse.omok.data.model.OmokGameDto

class OmokGameLocalDataSourceImpl(
    private val context: Context,
) : OmokGameLocalDataSource {
    companion object {
        private const val GAME_ID = 1
    }

    override fun save(omokGameDto: OmokGameDto) {
        val db = DbHelper(context, OmokContract).writableDatabase
        db.transaction {
            delete(OmokContract.TABLE_GAME_STATE, "${OmokContract.COLUMN_GAME_ID}=?", arrayOf(GAME_ID.toString()))
            omokGameDto.board.forEach { (pos, state) ->
                val values =
                    ContentValues().apply {
                        put(OmokContract.COLUMN_GAME_ID, GAME_ID)
                        put(OmokContract.COLUMN_POSITION_ROW, pos.first)
                        put(OmokContract.COLUMN_POSITION_COL, pos.second)
                        put(OmokContract.COLUMN_POSITION_STATE, state)
                        put(OmokContract.COLUMN_LAST_TURN, omokGameDto.lastTurn)
                    }
                insert(OmokContract.TABLE_GAME_STATE, null, values)
            }
        }
        db.close()
    }

    override fun load(): OmokGameDto? {
        val db = DbHelper(context, OmokContract).readableDatabase

        val cursor =
            db.query(
                OmokContract.TABLE_GAME_STATE,
                arrayOf(
                    OmokContract.COLUMN_POSITION_ROW,
                    OmokContract.COLUMN_POSITION_COL,
                    OmokContract.COLUMN_POSITION_STATE,
                    OmokContract.COLUMN_LAST_TURN,
                ),
                "${OmokContract.COLUMN_GAME_ID}=?",
                arrayOf(GAME_ID.toString()),
                null,
                null,
                null,
            )

        val board = mutableMapOf<Pair<Int, Int>, String>()
        var lastTurn: String? = null

        while (cursor.moveToNext()) {
            val row = cursor.getInt(cursor.getColumnIndexOrThrow(OmokContract.COLUMN_POSITION_ROW))
            val col = cursor.getInt(cursor.getColumnIndexOrThrow(OmokContract.COLUMN_POSITION_COL))
            val state = cursor.getString(cursor.getColumnIndexOrThrow(OmokContract.COLUMN_POSITION_STATE))
            lastTurn = cursor.getString(cursor.getColumnIndexOrThrow(OmokContract.COLUMN_LAST_TURN))
            board[Pair(row, col)] = state
        }

        cursor.close()
        db.close()

        return when (lastTurn != null && board.isNotEmpty()) {
            true -> OmokGameDto(lastTurn, board)
            false -> null
        }
    }

    override fun delete() {
        val db = DbHelper(context, OmokContract).writableDatabase
        db.delete(OmokContract.TABLE_GAME_STATE, "${OmokContract.COLUMN_GAME_ID}=?", arrayOf(GAME_ID.toString()))
        db.close()
    }
}
