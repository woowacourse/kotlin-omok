package woowacourse.omok.data.db

import android.content.ContentValues
import android.database.Cursor
import woowacourse.omok.domain.Position
import woowacourse.omok.domain.Stone
import woowacourse.omok.domain.StoneState

class BoardDao(
    private val dbHelper: DbHelper,
) {
    fun insert(
        stone: Stone,
        gameId: Long,
    ) {
        val db = dbHelper.writableDatabase

        val values =
            ContentValues().apply {
                put(BoardContract.COLUMN_NAME_GAME_ID, gameId)
                put(BoardContract.COLUMN_NAME_X, stone.position.x)
                put(BoardContract.COLUMN_NAME_Y, stone.position.y)
                put(BoardContract.COLUMN_NAME_STATE, stone.state.name)
            }

        db.insert(BoardContract.TABLE_NAME, null, values)
    }

    fun queryStones(gameId: Long): List<Stone> {
        val dbReader = dbHelper.readableDatabase
        val result = mutableListOf<Stone>()

        val cursor: Cursor =
            dbReader.query(
                BoardContract.TABLE_NAME,
                arrayOf(
                    BoardContract.COLUMN_NAME_GAME_ID,
                    BoardContract.COLUMN_NAME_X,
                    BoardContract.COLUMN_NAME_Y,
                    BoardContract.COLUMN_NAME_STATE,
                ),
                "${BoardContract.COLUMN_NAME_GAME_ID} = ?",
                arrayOf(gameId.toString()),
                null,
                null,
                null,
            )

        with(cursor) {
            while (moveToNext()) {
                val x = getInt(getColumnIndexOrThrow(BoardContract.COLUMN_NAME_X))
                val y = getInt(getColumnIndexOrThrow(BoardContract.COLUMN_NAME_Y))
                val state = getString(getColumnIndexOrThrow(BoardContract.COLUMN_NAME_STATE))

                val stone = Stone(Position(x, y), StoneState.valueOf(state))
                result.add(stone)
            }
        }
        cursor.close()
        return result
    }
}
