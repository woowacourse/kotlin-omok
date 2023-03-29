package woowacourse.omok.data.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.provider.BaseColumns
import woowacourse.omok.data.OmokContract.Room.CREATE_ROOM_TABLE
import woowacourse.omok.data.OmokContract.Room.DELETE_ROOM_TABLE
import woowacourse.omok.data.OmokContract.Room.TABLE_COLUMN_FIRST_USER_ID
import woowacourse.omok.data.OmokContract.Room.TABLE_COLUMN_SECOND_USER_ID
import woowacourse.omok.data.OmokContract.Room.TABLE_COLUMN_TITLE
import woowacourse.omok.data.OmokContract.Room.TABLE_NAME
import woowacourse.omok.data.OmokDbHelper
import woowacourse.omok.data.entity.RoomEntity

class RoomDao(context: Context) {
    private val omokDb by lazy { OmokDbHelper(context).readableDatabase }

    fun insertRoom(roomTitle: String, firstUserId: Int, secondUserId: Int): Int {
        val data = ContentValues()
        data.put(TABLE_COLUMN_TITLE, roomTitle)
        data.put(TABLE_COLUMN_FIRST_USER_ID, firstUserId)
        data.put(TABLE_COLUMN_SECOND_USER_ID, secondUserId)
        return omokDb.insert(TABLE_NAME, null, data).toInt()
    }

    fun getRooms(): List<RoomEntity> {
        val orderBy = "${BaseColumns._ID} DESC"
        val cursor = makeCursor(null, null, orderBy)
        val rooms = readRooms(cursor)
        cursor.close()
        return rooms
    }

    fun getRoom(id: Int): RoomEntity {
        val selection = "${BaseColumns._ID} == ?"
        val selectionArgs = arrayOf("$id")
        val cursor = makeCursor(selection, selectionArgs, null)
        val room = readRoom(cursor) ?: throw IllegalStateException("찾으려는 방이 없습니다.")
        cursor.close()
        return room
    }

    fun deleteRoom(id: Int) {
        omokDb.delete(TABLE_NAME, "${BaseColumns._ID}=?", arrayOf("$id"))
    }

    private fun readRoom(cursor: Cursor): RoomEntity? {
        if (!cursor.moveToNext()) return null
        val id = cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(TABLE_COLUMN_TITLE))
        val firstUserId =
            cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_COLUMN_FIRST_USER_ID))
        val secondUserId =
            cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_COLUMN_SECOND_USER_ID))
        return RoomEntity(id, title, firstUserId, secondUserId)
    }

    private fun readRooms(cursor: Cursor): List<RoomEntity> {
        val rooms = mutableListOf<RoomEntity>()
        while (true) {
            val room: RoomEntity = readRoom(cursor) ?: break
            rooms.add(room)
        }
        return rooms
    }

    private fun makeCursor(
        selection: String?,
        selectionArgs: Array<String>?,
        orderBy: String?,
    ): Cursor {
        return omokDb.query(
            TABLE_NAME,
            arrayOf(
                BaseColumns._ID,
                TABLE_COLUMN_TITLE,
                TABLE_COLUMN_FIRST_USER_ID,
                TABLE_COLUMN_SECOND_USER_ID,
            ),
            selection,
            selectionArgs,
            null,
            null,
            orderBy,
        )
    }

    fun closeDb() {
        omokDb.close()
    }

    fun clear() {
        omokDb.execSQL(DELETE_ROOM_TABLE)
        omokDb.execSQL(CREATE_ROOM_TABLE)
    }
}
