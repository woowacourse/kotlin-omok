package woowacourse.omok.dbHelper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.omok.data.Player
import woowacourse.omok.data.Room
import woowacourse.omok.dbHelper.OmokRoomConstract.TABLE_COLUMN_GAME_ID
import woowacourse.omok.dbHelper.OmokRoomConstract.TABLE_COLUMN_PLAYER_ID
import woowacourse.omok.dbHelper.OmokRoomConstract.TABLE_COLUMN_STATUS
import woowacourse.omok.dbHelper.OmokRoomConstract.TABLE_COLUMN_TIME
import woowacourse.omok.dbHelper.OmokRoomConstract.TABLE_COLUMN_TITLE
import woowacourse.omok.dbHelper.OmokRoomConstract.TABLE_NAME_ROOM

class OmokRoomDbHelper(
    context: Context?,
) : SQLiteOpenHelper(context, "ark.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE IF NOT EXISTS $TABLE_NAME_ROOM (" +
                "$TABLE_COLUMN_GAME_ID INTEGER PRIMARY KEY UNIQUE ON CONFLICT REPLACE," +
                "$TABLE_COLUMN_PLAYER_ID LONG UNIQUE," +
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

    fun insert(room: Room) {
        val wDb = this.writableDatabase

        wDb.insert(
            TABLE_NAME_ROOM,
            null,
            getRoomContentValues(room),
        )
    }

    fun getRooms(context: Context): List<Room> {
        val rDb = this.readableDatabase
        val cursor = rDb.customQuery(
            table = TABLE_NAME_ROOM,
            columns = arrayOf(TABLE_COLUMN_GAME_ID, TABLE_COLUMN_PLAYER_ID, TABLE_COLUMN_TITLE, TABLE_COLUMN_STATUS, TABLE_COLUMN_TIME),
        )
        val rooms = moveAllRooms(cursor, context)
        cursor.close()
        return rooms
    }

    private fun getRoomContentValues(room: Room) = ContentValues().apply {
        put(TABLE_COLUMN_PLAYER_ID, room.player.id)
        put(TABLE_COLUMN_TITLE, room.title)
        put(TABLE_COLUMN_STATUS, room.status)
        put(TABLE_COLUMN_TIME, room.time)
    }

    private fun SQLiteDatabase.customQuery(
        table: String,
        columns: Array<String> = arrayOf(),
        selection: String? = null,
        selectionArgs: Array<String>? = null,
        groupBy: String? = null,
        having: String? = null,
        orderBy: String? = null,
        limit: String? = null,
    ): Cursor = query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit)

    private fun moveAllRooms(cursor: Cursor, context: Context): List<Room> {
        val rooms = mutableListOf<Room>()
        while (cursor.moveToNext()) {
            val playerId = cursor.getLong(cursor.getColumnIndexOrThrow(TABLE_COLUMN_PLAYER_ID))

            OmokPlayerDbHelper(context).getPlayer(playerId)
                ?.let { player -> rooms.add(getRoomFrom(cursor, player)) }
        }
        return rooms
    }
    private fun getRoomFrom(cursor: Cursor, player: Player) = Room(
        title = cursor.getString(cursor.getColumnIndexOrThrow(TABLE_COLUMN_TITLE)),
        gameId = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_COLUMN_GAME_ID)),
        player = player,
        status = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_COLUMN_STATUS)),
        time = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_COLUMN_TIME)),
    )
}
