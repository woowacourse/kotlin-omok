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
        omokDbHelper.deleteGameState(game.gameId)

        game.board.positions.forEach { (pos, state) ->
            val values =
                ContentValues().apply {
                    put(OmokContract.COLUMN_GAME_ID, game.gameId)
                    put(OmokContract.COLUMN_POSITION_ROW, pos.first)
                    put(OmokContract.COLUMN_POSITION_COL, pos.second)
                    put(OmokContract.COLUMN_POSITION_STATE, state)
                    put(OmokContract.COLUMN_LAST_TURN, game.lastTurn)
                }
            omokDbHelper.insertGameState(values)
        }
    }

    override fun fetchGame(gameId: Int): OmokGameDto? {
        val cursor = omokDbHelper.queryGameState(gameId)

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

    override fun deleteGame(gameId: Int) {
        omokDbHelper.deleteGameState(gameId)
    }
}
