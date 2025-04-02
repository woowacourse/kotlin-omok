package woowacourse.omok.data.dao

import android.content.ContentValues
import woowacourse.omok.data.db.OmokContract
import woowacourse.omok.data.db.OmokDbHelper
import woowacourse.omok.data.model.OmokBoardDto
import woowacourse.omok.data.model.OmokGameDto

class OmokGameDaoImpl(
    private val omokDbHelper: OmokDbHelper,
) : OmokGameDao {
    override fun saveGame(game: OmokGameDto) {
        omokDbHelper.writableDatabase.use { db ->
            db.delete(
                OmokContract.TABLE_GAME_STATE,
                "${OmokContract.COLUMN_GAME_ID}=?",
                arrayOf(game.gameId.toString()),
            )

            game.board.matrix.forEach { (pos, state) ->
                val values =
                    ContentValues().apply {
                        put(OmokContract.COLUMN_GAME_ID, game.gameId)
                        put(OmokContract.COLUMN_POSITION_ROW, pos.first)
                        put(OmokContract.COLUMN_POSITION_COL, pos.second)
                        put(OmokContract.COLUMN_POSITION_STATE, state)
                        put(OmokContract.COLUMN_LAST_TURN, game.lastTurn)
                    }
                db.insert(OmokContract.TABLE_GAME_STATE, null, values)
            }
        }
    }

    override fun fetchGame(gameId: Int): OmokGameDto? {
        omokDbHelper.readableDatabase.use { db ->
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
                true -> OmokGameDto(gameId, lastTurn, OmokBoardDto(board))
                false -> null
            }
        }
    }

    override fun deleteGame(gameId: Int) {
        omokDbHelper.writableDatabase.use { db ->
            db.delete(
                OmokContract.TABLE_GAME_STATE,
                "${OmokContract.COLUMN_GAME_ID}=?",
                arrayOf(gameId.toString()),
            )
        }
    }
}
