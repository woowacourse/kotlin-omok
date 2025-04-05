package woowacourse.omok.data.dao

import android.content.ContentValues
import woowacourse.omok.data.db.OmokContract
import woowacourse.omok.data.db.OmokDbHelper
import woowacourse.omok.data.model.OmokBoardDto
import woowacourse.omok.data.model.OmokGameDto
import woowacourse.omok.data.model.OmokGamesDto

class OmokGameDaoImpl(
    private val omokDbHelper: OmokDbHelper,
) : OmokGameDao {
    override fun saveGame(game: OmokGameDto) {
        deleteGame(game.id)

        game.board.positions.forEach { (pos, state) ->
            val values =
                ContentValues().apply {
                    put(OmokContract.COLUMN_GAME_ID, game.id)
                    put(OmokContract.COLUMN_POSITION_ROW, pos.first)
                    put(OmokContract.COLUMN_POSITION_COL, pos.second)
                    put(OmokContract.COLUMN_POSITION_STATE, state)
                    put(OmokContract.COLUMN_LAST_TURN, game.lastTurn)
                    put(OmokContract.COLUMN_HOST, game.host)
                }
            omokDbHelper.insertGameState(values)
        }
    }

    override fun fetchGame(gameId: Int): OmokGameDto? {
        val cursor = omokDbHelper.queryGameState(gameId)

        val board = mutableMapOf<Pair<Int, Int>, String>()
        var lastTurn: String? = null
        var host: String? = null

        while (cursor.moveToNext()) {
            val row = cursor.getInt(cursor.getColumnIndexOrThrow(OmokContract.COLUMN_POSITION_ROW))
            val col = cursor.getInt(cursor.getColumnIndexOrThrow(OmokContract.COLUMN_POSITION_COL))
            val state = cursor.getString(cursor.getColumnIndexOrThrow(OmokContract.COLUMN_POSITION_STATE))
            lastTurn = cursor.getString(cursor.getColumnIndexOrThrow(OmokContract.COLUMN_LAST_TURN))
            host = cursor.getString(cursor.getColumnIndexOrThrow(OmokContract.COLUMN_HOST))
            board[Pair(row, col)] = state
        }

        cursor.close()

        return when (lastTurn != null && host != null && board.isNotEmpty()) {
            true -> OmokGameDto(gameId, host, lastTurn, OmokBoardDto(board))
            false -> null
        }
    }

    override fun deleteGame(gameId: Int) {
        omokDbHelper.deleteGameState(gameId)
    }

    override fun createGame(game: OmokGameDto): Int {
        val newId = generateNewGameId()
        val newGame = game.copy(id = newId)
        saveGame(newGame)
        return newId
    }

    private fun generateNewGameId(): Int {
        val db = omokDbHelper.readableDatabase
        val cursor = db.rawQuery(OmokContract.SQL_SELECT_MAX_ID, null)
        val newId = if (cursor.moveToFirst()) cursor.getInt(0) + 1 else 1
        cursor.close()
        return newId
    }

    override fun fetchAllGames(): OmokGamesDto {
        val db = omokDbHelper.readableDatabase
        val cursor = db.rawQuery(OmokContract.SQL_SELECT_ALL_GAMES, null)

        val gamesMap = mutableMapOf<Int, MutableMap<Pair<Int, Int>, String>>()
        val lastTurnMap = mutableMapOf<Int, String>()
        val hostMap = mutableMapOf<Int, String>()

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(OmokContract.COLUMN_GAME_ID))
            val row = cursor.getInt(cursor.getColumnIndexOrThrow(OmokContract.COLUMN_POSITION_ROW))
            val col = cursor.getInt(cursor.getColumnIndexOrThrow(OmokContract.COLUMN_POSITION_COL))
            val state = cursor.getString(cursor.getColumnIndexOrThrow(OmokContract.COLUMN_POSITION_STATE))
            val lastTurn = cursor.getString(cursor.getColumnIndexOrThrow(OmokContract.COLUMN_LAST_TURN))
            val host = cursor.getString(cursor.getColumnIndexOrThrow(OmokContract.COLUMN_HOST))

            if (state.isNotBlank()) {
                gamesMap.getOrPut(id) { mutableMapOf() }[Pair(row, col)] = state
            }

            lastTurnMap[id] = lastTurn
            hostMap[id] = host
        }

        cursor.close()

        return OmokGamesDto(
            gamesMap.map { (id, board) ->
                OmokGameDto(
                    id = id,
                    host = hostMap[id] ?: "Unknown",
                    lastTurn = lastTurnMap[id] ?: "BLACK",
                    board = OmokBoardDto(board),
                )
            },
        )
    }
}
