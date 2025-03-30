package woowacourse.omok.data.dao

import android.content.ContentValues
import woowacourse.omok.data.db.DbHelper
import woowacourse.omok.data.db.OmokContract
import woowacourse.omok.data.model.OmokGameDto

class OmokGameDaoImpl(
    private val dbHelper: DbHelper,
    private val gameId: Int = 1,
) : OmokGameDao {
    override fun saveGame(game: OmokGameDto) {
        dbHelper.writableDatabase.use { db ->
            db.delete(OmokContract.TABLE_GAME_STATE, "${OmokContract.COLUMN_GAME_ID}=?", arrayOf(gameId.toString()))
            game.board.forEach { (pos, state) ->
                val values =
                    ContentValues().apply {
                        put(OmokContract.COLUMN_GAME_ID, gameId)
                        put(OmokContract.COLUMN_POSITION_ROW, pos.first)
                        put(OmokContract.COLUMN_POSITION_COL, pos.second)
                        put(OmokContract.COLUMN_POSITION_STATE, state)
                        put(OmokContract.COLUMN_LAST_TURN, game.lastTurn)
                    }
                db.insert(OmokContract.TABLE_GAME_STATE, null, values)
            }
        }
    }

    override fun fetchGame(): OmokGameDto? {
        dbHelper.readableDatabase.use { db ->
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
                    arrayOf(gameId.toString()),
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

            return when (lastTurn != null && board.isNotEmpty()) {
                true -> OmokGameDto(lastTurn, board)
                false -> null
            }
        }
    }

    override fun deleteGame() {
        dbHelper.writableDatabase.use { db ->
            db.delete(OmokContract.TABLE_GAME_STATE, "${OmokContract.COLUMN_GAME_ID}=?", arrayOf(gameId.toString()))
        }
    }
}
