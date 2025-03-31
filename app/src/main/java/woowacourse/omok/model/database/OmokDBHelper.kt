package woowacourse.omok.model.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.database.sqlite.transaction
import woowacourse.omok.model.database.OmokDBContract.GameRoomsTable
import woowacourse.omok.model.database.OmokDBContract.PlayerTable
import woowacourse.omok.model.database.OmokDBContract.StonesTable
import woowacourse.omok.model.gameRoom.GameRoom
import java.time.LocalDateTime

class OmokDBHelper(
    context: Context,
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(StonesTable.SQL_CREATE_ENTRIES)
        db.execSQL(GameRoomsTable.SQL_CREATE_ENTRIES)
        db.execSQL(PlayerTable.SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int,
    ) {
        db.execSQL(StonesTable.SQL_DELETE_ENTRIES)
        db.execSQL(GameRoomsTable.SQL_DELETE_ENTRIES)
        db.execSQL(PlayerTable.SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int,
    ) {
        onUpgrade(db, oldVersion, newVersion)
    }

    fun roomWithStonesDelete(roomId: Int) {
        val db = writableDatabase
        db.transaction {
            val roomWhereClause = "${GameRoomsTable.COLUMN_ROOM_ID} = ?"
            val roomWhereArgs = arrayOf(roomId.toString())
            delete(GameRoomsTable.TABLE_NAME, roomWhereClause, roomWhereArgs)
        }
        stonesDelete(roomId)
    }

    fun stonesDelete(roomId: Int) {
        val db = writableDatabase
        db.transaction {
            val stonesWhereClause = "${StonesTable.COLUMN_ROOM_ID} = ?"
            val stonesWhereArgs = arrayOf(roomId.toString())
            delete(StonesTable.TABLE_NAME, stonesWhereClause, stonesWhereArgs)
        }
    }

    fun addPlayerHistory(
        name: String,
        playCount: Int = 0,
        blackWinCount: Int = 0,
        whiteWinCount: Int = 0,
    ) {
        val db = writableDatabase

        if (isPlayerNameExitInPlayerDB(name)) {
            val updateQuery =
                """
                UPDATE ${PlayerTable.TABLE_NAME} SET 
                ${PlayerTable.COLUMN_PLAY_COUNT} = ${PlayerTable.COLUMN_PLAY_COUNT} + ?, 
                ${PlayerTable.COLUMN_BLACK_WIN_COUNT} = ${PlayerTable.COLUMN_BLACK_WIN_COUNT} + ?, 
                ${PlayerTable.COLUMN_WHITE_WIN_COUNT} = ${PlayerTable.COLUMN_WHITE_WIN_COUNT} + ? 
                WHERE ${PlayerTable.COLUMN_PLAYER_NAME} = ?
                """.trimIndent()

            db.execSQL(
                updateQuery,
                arrayOf(playCount, blackWinCount, whiteWinCount, name),
            )
        } else {
            val values =
                ContentValues().apply {
                    put(PlayerTable.COLUMN_PLAYER_NAME, name)
                    put(PlayerTable.COLUMN_PLAY_COUNT, playCount)
                    put(PlayerTable.COLUMN_BLACK_WIN_COUNT, blackWinCount)
                    put(PlayerTable.COLUMN_WHITE_WIN_COUNT, whiteWinCount)
                }

            db.insert(PlayerTable.TABLE_NAME, null, values)
        }
    }

    private fun isPlayerNameExitInPlayerDB(name: String): Boolean {
        val db = writableDatabase

        val query =
            "SELECT EXISTS(SELECT 1 FROM ${PlayerTable.TABLE_NAME} WHERE ${PlayerTable.COLUMN_PLAYER_NAME} = ?)"
        val cursor = db.rawQuery(query, arrayOf(name))

        cursor.use {
            it.moveToFirst()
            val exists = it.getInt(FIRST_COLUMN_INDEX)
            return exists == TRUE_IN_SQLITE
        }
    }

    fun getPlayerInfo(name: String): PlayerInfo? {
        val db = readableDatabase
        val projection =
            arrayOf(
                PlayerTable.COLUMN_PLAYER_NAME,
                PlayerTable.COLUMN_PLAY_COUNT,
                PlayerTable.COLUMN_BLACK_WIN_COUNT,
                PlayerTable.COLUMN_WHITE_WIN_COUNT,
            )

        val selection = "${PlayerTable.COLUMN_PLAYER_NAME} = ?"
        val selectionArgs = arrayOf(name)

        return db
            .query(
                PlayerTable.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null,
            ).use { cursor ->
                if (cursor.moveToFirst()) {
                    PlayerInfo(
                        name = cursor.getString(cursor.getColumnIndexOrThrow(PlayerTable.COLUMN_PLAYER_NAME)),
                        playCount = cursor.getInt(cursor.getColumnIndexOrThrow(PlayerTable.COLUMN_PLAY_COUNT)),
                        blackWinCount = cursor.getInt(cursor.getColumnIndexOrThrow(PlayerTable.COLUMN_BLACK_WIN_COUNT)),
                        whiteWinCount = cursor.getInt(cursor.getColumnIndexOrThrow(PlayerTable.COLUMN_WHITE_WIN_COUNT)),
                    )
                } else {
                    null
                }
            }
    }

    fun fetchDBGameRooms(): List<GameRoom> {
        val gameRooms = mutableListOf<GameRoom>()
        executeGameRoomsFetchQuery().use { cursor ->
            val roomIdIndex = cursor.getColumnIndexOrThrow(GameRoomsTable.COLUMN_ROOM_ID)
            val blackNameIndex = cursor.getColumnIndexOrThrow(GameRoomsTable.COLUMN_BLACK_PLAYER_NAME)
            val whiteNameIndex = cursor.getColumnIndexOrThrow(GameRoomsTable.COLUMN_WHITE_PLAYER_NAME)
            val timeIndex = cursor.getColumnIndexOrThrow(GameRoomsTable.COLUMN_LAST_PLAY_TIME)

            while (cursor.moveToNext()) {
                val roomId = cursor.getInt(roomIdIndex)
                val blackPlayerName = cursor.getString(blackNameIndex)
                val whitePlayerName = cursor.getString(whiteNameIndex)
                val lastPlayTime = LocalDateTime.parse(cursor.getString(timeIndex), OmokDBContract.dbTimeFormatter)

                gameRooms.add(
                    GameRoom(
                        id = roomId,
                        blackStonePlayerName = blackPlayerName,
                        whiteStonePlayerName = whitePlayerName,
                        lastPlayTime = lastPlayTime,
                    ),
                )
            }
        }

        return gameRooms
    }

    private fun executeGameRoomsFetchQuery(): Cursor {
        val db = readableDatabase
        val projection =
            arrayOf(
                GameRoomsTable.COLUMN_ROOM_ID,
                GameRoomsTable.COLUMN_BLACK_PLAYER_NAME,
                GameRoomsTable.COLUMN_WHITE_PLAYER_NAME,
                GameRoomsTable.COLUMN_LAST_PLAY_TIME,
            )

        return db.query(
            GameRoomsTable.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            "${GameRoomsTable.COLUMN_LAST_PLAY_TIME} DESC",
        )
    }

    fun fetchNewGameRoom(
        blackPlayerName: String,
        whitePlayerName: String,
    ): GameRoom? {
        val newRoomId = makeNewRoomAndGetId(blackPlayerName, whitePlayerName)

        return executeGameRoomFetchQuery(newRoomId).use { cursor ->
            if (cursor.moveToFirst()) {
                val lastPlayTime =
                    cursor.getString(cursor.getColumnIndexOrThrow(GameRoomsTable.COLUMN_LAST_PLAY_TIME))

                GameRoom(
                    id = newRoomId.toInt(),
                    blackStonePlayerName = blackPlayerName,
                    whiteStonePlayerName = whitePlayerName,
                    lastPlayTime = LocalDateTime.parse(lastPlayTime, OmokDBContract.dbTimeFormatter),
                )
            } else {
                null
            }
        }
    }

    private fun executeGameRoomFetchQuery(roomId: Long): Cursor {
        val db = writableDatabase

        val projection =
            arrayOf(
                GameRoomsTable.COLUMN_ROOM_ID,
                GameRoomsTable.COLUMN_BLACK_PLAYER_NAME,
                GameRoomsTable.COLUMN_WHITE_PLAYER_NAME,
                GameRoomsTable.COLUMN_LAST_PLAY_TIME,
            )

        val selection = "${GameRoomsTable.COLUMN_ROOM_ID} = ?"
        val selectionArgs = arrayOf(roomId.toString())

        return db
            .query(
                GameRoomsTable.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null,
            )
    }

    private fun makeNewRoomAndGetId(
        blackPlayName: String,
        whitePlayerName: String,
    ): Long {
        val db = writableDatabase

        val values =
            ContentValues().apply {
                put(GameRoomsTable.COLUMN_BLACK_PLAYER_NAME, blackPlayName)
                put(GameRoomsTable.COLUMN_WHITE_PLAYER_NAME, whitePlayerName)
            }

        return db.insert(GameRoomsTable.TABLE_NAME, null, values)
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Omok.db"

        private const val FIRST_COLUMN_INDEX = 0
        private const val TRUE_IN_SQLITE = 1
    }
}
