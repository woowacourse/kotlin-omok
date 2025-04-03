package woowacourse.omok.data.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.omok.domain.GameRoom
import woowacourse.omok.domain.Position
import woowacourse.omok.domain.Stone
import woowacourse.omok.domain.StoneState

class DbHelper(
    context: Context,
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    private fun createGameTable(db: SQLiteDatabase) {
        db.execSQL(GameContract.SQL_CREATE_ENTRIES)
    }

    private fun createBoardTable(db: SQLiteDatabase) {
        db.execSQL(BoardContract.SQL_CREATE_ENTRIES)
    }

    private fun deleteGameTable(db: SQLiteDatabase) {
        db.execSQL(GameContract.SQL_DELETE_ENTRIES)
    }

    private fun deleteBoardTable(db: SQLiteDatabase) {
        db.execSQL(BoardContract.SQL_DELETE_ENTRIES)
    }

    override fun onCreate(db: SQLiteDatabase) {
        createGameTable(db)
        createBoardTable(db)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int,
    ) {
        deleteBoardTable(db)
        deleteGameTable(db)
        onCreate(db)
    }

    override fun onDowngrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int,
    ) {
        onUpgrade(db, oldVersion, newVersion)
    }

    // BoardDao
    fun insertStone(
        stone: Stone,
        gameId: Long,
    ) {
        writableDatabase.use { db ->
            val values =
                ContentValues().apply {
                    put(BoardContract.COLUMN_NAME_GAME_ID, gameId)
                    put(BoardContract.COLUMN_NAME_X, stone.position.x)
                    put(BoardContract.COLUMN_NAME_Y, stone.position.y)
                    put(BoardContract.COLUMN_NAME_STATE, stone.state.name)
                }
            db.insert(BoardContract.TABLE_NAME, null, values)
        }
    }

    fun queryStones(gameId: Long): List<Stone> {
        val result = mutableListOf<Stone>()

        readableDatabase.use { db ->
            val cursor =
                db.query(
                    BoardContract.TABLE_NAME,
                    arrayOf(
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

            cursor.use {
                while (it.moveToNext()) {
                    val x = it.getInt(it.getColumnIndexOrThrow(BoardContract.COLUMN_NAME_X))
                    val y = it.getInt(it.getColumnIndexOrThrow(BoardContract.COLUMN_NAME_Y))
                    val state =
                        it.getString(it.getColumnIndexOrThrow(BoardContract.COLUMN_NAME_STATE))

                    result.add(Stone(Position(x, y), StoneState.valueOf(state)))
                }
            }
        }
        return result
    }

    // GameDao
    fun insertGame(roomName: String): Long {
        val db = writableDatabase
        val values =
            ContentValues().apply {
                put(GameContract.COLUMN_NAME_GAME_NAME, roomName)
            }
        return db.insert(GameContract.TABLE_NAME, null, values)
    }

    fun queryGames(): List<GameRoom> {
        val result = mutableListOf<GameRoom>()
        val dbReader = readableDatabase
        val cursor: Cursor =
            dbReader.query(
                GameContract.TABLE_NAME,
                arrayOf(
                    GameContract.COLUMN_NAME_GAME_ID,
                    GameContract.COLUMN_NAME_GAME_NAME,
                ),
                null,
                null,
                null,
                null,
                null,
            )

        with(cursor) {
            while (moveToNext()) {
                val gameId =
                    getLong(getColumnIndexOrThrow(GameContract.COLUMN_NAME_GAME_ID)).toInt()
                val title = getString(getColumnIndexOrThrow(GameContract.COLUMN_NAME_GAME_NAME))
                result.add(GameRoom(gameId, title))
            }
        }
        cursor.close()
        return result
    }

    fun deleteGame(gameId: Int): Boolean {
        val db = writableDatabase
        val deletedRows =  db.delete(
            GameContract.TABLE_NAME,
            "${GameContract.COLUMN_NAME_GAME_ID} = ?",
            arrayOf(gameId.toString()),
        )
        return deletedRows > 0
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Omok.db"
    }
}
