package woowacourse.omok.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.omok.domain.board.Point
import woowacourse.omok.domain.board.StoneColor

class OmokDatabaseHelper(
    context: Context,
) : SQLiteOpenHelper(context, OmokContract.DATABASE_NAME, null, OmokContract.DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(OmokContract.CREATE_GAMES_TABLE)
        db.execSQL(OmokContract.CREATE_BOARD_TABLE)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int,
    ) {
        db.execSQL("DROP TABLE IF EXISTS ${OmokContract.TABLE_NAME_GAMES}")
        db.execSQL("DROP TABLE IF EXISTS ${OmokContract.TABLE_NAME_BOARD}")
        onCreate(db)
    }

    fun addGame(gameId: Int) {
        writableDatabase.use { db ->
            val values =
                ContentValues().apply {
                    put(OmokContract.COLUMN_NAME_ID, gameId)
                }
            db.insert(OmokContract.TABLE_NAME_GAMES, null, values)
        }
    }

    fun deleteGame(gameId: Int) {
        writableDatabase.use { db ->
            db.delete(
                OmokContract.TABLE_NAME_BOARD,
                "${OmokContract.COLUMN_NAME_GAME_ID_FK} = ?",
                arrayOf(gameId.toString()),
            )
            db.delete(
                OmokContract.TABLE_NAME_GAMES,
                "${OmokContract.COLUMN_NAME_ID} = ?",
                arrayOf(gameId.toString()),
            )
        }
    }

    fun getGameIds(): List<Int> =
        getGameCursor(readableDatabase).use { cursor ->
            val gameIds = mutableListOf<Int>()
            while (cursor.moveToNext()) {
                val index = cursor.getColumnIndex(OmokContract.COLUMN_NAME_ID)
                val id = cursor.getInt(index)
                gameIds.add(id)
            }

            cursor.close()
            gameIds
        }

    fun saveMove(
        gameId: Int,
        move: Pair<Point, StoneColor>,
    ) {
        writableDatabase.use { db ->
            val values = createBoardContentValues(gameId, move)
            db.insert(OmokContract.TABLE_NAME_BOARD, null, values)
        }
    }

    fun getMoves(gameId: Int): List<Pair<Point, StoneColor>> =
        getMoveCursor(readableDatabase, gameId).use { cursor ->
            val moves = mutableListOf<Pair<Point, StoneColor>>()
            while (cursor.moveToNext()) {
                val move = cursorToMove(cursor)
                moves.add(move)
            }

            cursor.close()
            moves
        }

    private fun getGameCursor(db: SQLiteDatabase): Cursor =
        db.query(
            OmokContract.TABLE_NAME_GAMES,
            arrayOf(OmokContract.COLUMN_NAME_ID),
            null,
            null,
            null,
            null,
            null,
        )

    private fun getMoveCursor(
        db: SQLiteDatabase,
        gameId: Int,
    ): Cursor =
        db.query(
            OmokContract.TABLE_NAME_BOARD,
            arrayOf(
                OmokContract.COLUMN_NAME_X,
                OmokContract.COLUMN_NAME_Y,
                OmokContract.COLUMN_NAME_COLOR,
            ),
            "${OmokContract.COLUMN_NAME_GAME_ID_FK} = ?",
            arrayOf(gameId.toString()),
            null,
            null,
            null,
        )

    private fun createBoardContentValues(
        gameId: Int,
        move: Pair<Point, StoneColor>,
    ): ContentValues =
        ContentValues().apply {
            put(OmokContract.COLUMN_NAME_GAME_ID_FK, gameId)
            put(OmokContract.COLUMN_NAME_X, move.first.x)
            put(OmokContract.COLUMN_NAME_Y, move.first.y)
            put(OmokContract.COLUMN_NAME_COLOR, move.second.toString())
        }

    private fun cursorToMove(cursor: Cursor): Pair<Point, StoneColor> {
        val x = cursor.getInt(0)
        val y = cursor.getInt(1)
        val color = cursor.getString(2)

        val stoneColor = StoneColor.entries.find { it.toString() == color } ?: StoneColor.NONE
        return Point(x, y) to stoneColor
    }
}
