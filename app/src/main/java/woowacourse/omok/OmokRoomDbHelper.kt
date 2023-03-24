package woowacourse.omok

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.omok.OmokRoomConstract.TABLE_COLUMN_GAME_ID
import woowacourse.omok.OmokRoomConstract.TABLE_COLUMN_PLAYER_ID
import woowacourse.omok.OmokRoomConstract.TABLE_COLUMN_STATUS
import woowacourse.omok.OmokRoomConstract.TABLE_COLUMN_TIME
import woowacourse.omok.OmokRoomConstract.TABLE_COLUMN_TITLE
import woowacourse.omok.OmokRoomConstract.TABLE_NAME_ROOM
import woowacourse.omok.data.Room

class OmokRoomDbHelper(
    context: Context?,
) : SQLiteOpenHelper(context, "ark.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE IF NOT EXISTS $TABLE_NAME_ROOM (" +
                "$TABLE_COLUMN_GAME_ID INTEGER PRIMARY KEY UNIQUE ON CONFLICT REPLACE," +
                "$TABLE_COLUMN_PLAYER_ID INTEGER," +
                "$TABLE_COLUMN_TITLE TEXT," +
                "$TABLE_COLUMN_STATUS INTEGER," +
                "$TABLE_COLUMN_TIME INTEGER" +
                ");",
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_ROOM")
        onCreate(db)
    }

    private fun checkAlreadyExist(playerId: Int): Boolean {
        val rDb = this.readableDatabase

        val cursor = rDb.query(
            TABLE_NAME_ROOM,
            arrayOf(TABLE_COLUMN_PLAYER_ID),
            "$TABLE_COLUMN_PLAYER_ID = ?",
            arrayOf(playerId.toString()),
            null,
            null,
            null,
        )

        return cursor.count > 0
    }

    fun insertOrReplace(room: Room) {
        room.player.id?.let {
            when {
                checkAlreadyExist(it) -> update(room)
                else -> insert(room)
            }
        }
    }

    private fun getRoomContentValues(room: Room) = ContentValues().apply {
        put(TABLE_COLUMN_PLAYER_ID, room.player.id)
        put(TABLE_COLUMN_TITLE, room.title)
        put(TABLE_COLUMN_STATUS, room.status)
        put(TABLE_COLUMN_TIME, room.time)
    }

    private fun insert(room: Room) {
        val wDb = this.writableDatabase

        val values = getRoomContentValues(room)

        wDb.insert(
            TABLE_NAME_ROOM,
            null,
            values,
        )
    }

    private fun update(room: Room) {
        val wDb = this.writableDatabase

        val values = getRoomContentValues(room)

        wDb.update(
            TABLE_NAME_ROOM,
            values,
            "$TABLE_COLUMN_GAME_ID = ?",
            arrayOf(room.gameId.toString()),
        )
    }

    fun getRooms(context: Context): List<Room> {
        val rDb = this.readableDatabase
        val cursor = rDb.query(
            TABLE_NAME_ROOM,
            arrayOf(
                TABLE_COLUMN_GAME_ID,
                TABLE_COLUMN_PLAYER_ID,
                TABLE_COLUMN_TITLE,
                TABLE_COLUMN_STATUS,
                TABLE_COLUMN_TIME,
            ),
            null,
            null,
            null,
            null,
            null,
        )

        val roomInfo = mutableListOf<Room>()

        with(cursor) {
            while (moveToNext()) {
                val playerId = getInt(getColumnIndexOrThrow(TABLE_COLUMN_PLAYER_ID))

                OmokPlayerDbHelper(context).getPlayer(playerId)?.let {
                    roomInfo.add(
                        Room(
                            title = getString(getColumnIndexOrThrow(TABLE_COLUMN_TITLE)),
                            gameId = getInt(getColumnIndexOrThrow(TABLE_COLUMN_GAME_ID)),
                            player = it,
                            status = getInt(getColumnIndexOrThrow(TABLE_COLUMN_STATUS)),
                            time = getInt(getColumnIndexOrThrow(TABLE_COLUMN_TIME)),
                        ),
                    )
                }
            }
        }

        return roomInfo
    }
}
