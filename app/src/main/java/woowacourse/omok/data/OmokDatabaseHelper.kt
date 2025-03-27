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
) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(GamesTableContract.CREATE_TABLE)
        db.execSQL(BoardTableContract.CREATE_TABLE)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int,
    ) {
        db.execSQL("DROP TABLE IF EXISTS ${GamesTableContract.TABLE_NAME}")
        db.execSQL("DROP TABLE IF EXISTS ${BoardTableContract.TABLE_NAME}")
        onCreate(db)
    }

    fun addGame(gameId: Int) {
        writableDatabase.use { db ->
            val values =
                ContentValues().apply {
                    put(GamesTableContract.COLUMN_NAME_ID, gameId)
                }
            db.insert(GamesTableContract.TABLE_NAME, null, values)
        }
    }

    fun deleteGame(gameId: Int) {
        writableDatabase.use { db ->
            db.delete(
                BoardTableContract.TABLE_NAME,
                "${BoardTableContract.COLUMN_NAME_GAME_ID_FK} = ?",
                arrayOf(gameId.toString()),
            )
            db.delete(
                GamesTableContract.TABLE_NAME,
                "${GamesTableContract.COLUMN_NAME_ID} = ?",
                arrayOf(gameId.toString()),
            )
        }
    }

    fun getGameIds(): List<Int> =
        getGameCursor(readableDatabase).use { cursor ->
            val gameIds = mutableListOf<Int>()
            while (cursor.moveToNext()) {
                val index = cursor.getColumnIndex(GamesTableContract.COLUMN_NAME_ID)
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
            db.insert(BoardTableContract.TABLE_NAME, null, values)
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
            GamesTableContract.TABLE_NAME,
            arrayOf(GamesTableContract.COLUMN_NAME_ID),
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
            BoardTableContract.TABLE_NAME,
            arrayOf(
                BoardTableContract.COLUMN_NAME_X,
                BoardTableContract.COLUMN_NAME_Y,
                BoardTableContract.COLUMN_NAME_COLOR,
            ),
            "${BoardTableContract.COLUMN_NAME_GAME_ID_FK} = ?",
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
            put(BoardTableContract.COLUMN_NAME_GAME_ID_FK, gameId)
            put(BoardTableContract.COLUMN_NAME_X, move.first.x)
            put(BoardTableContract.COLUMN_NAME_Y, move.first.y)
            put(BoardTableContract.COLUMN_NAME_COLOR, move.second.toString())
        }

    private fun cursorToMove(cursor: Cursor): Pair<Point, StoneColor> {
        val x = cursor.getInt(0)
        val y = cursor.getInt(1)
        val color = cursor.getString(2)

        val stoneColor = StoneColor.entries.find { it.toString() == color } ?: StoneColor.NONE
        return Point(x, y) to stoneColor
    }

    companion object {
        private const val DB_NAME = "OmokGame.db"
        private const val DB_VERSION = 1
    }
}
