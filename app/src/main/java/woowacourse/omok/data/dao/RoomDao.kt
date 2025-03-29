package woowacourse.omok.data.dao

import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf
import woowacourse.omok.data.db.room.RoomEntity
import woowacourse.omok.data.db.room.RoomSchema.RoomsContract

class RoomDao(private val dbHelper: SQLiteOpenHelper) {
    fun insertRoom(entity: RoomEntity): Long {
        dbHelper.writableDatabase.use { db ->
            val values =
                contentValuesOf(
                    RoomsContract.COLUMN_NAME_ROOM_NAME to entity.roomName,
                )
            return db.insert(RoomsContract.ROOM_TABLE_NAME, null, values)
        }
    }

    fun getAllRooms(): List<RoomEntity> {
        val rooms = mutableListOf<RoomEntity>()
        dbHelper.readableDatabase
            .use { db ->
                val cursor =
                    db.query(
                        RoomsContract.ROOM_TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                    )

                cursor.use {
                    while (it.moveToNext()) {
                        val roomId =
                            it.getLong(it.getColumnIndexOrThrow(RoomsContract.COLUMN_NAME_ROOM_ID))
                        val roomName =
                            it.getString(it.getColumnIndexOrThrow(RoomsContract.COLUMN_NAME_ROOM_NAME))
                        rooms.add(RoomEntity(roomId, roomName))
                    }
                }
            }
        return rooms
    }

    fun deleteRoom(roomId: Long) {
        dbHelper.writableDatabase.use { db ->
            db.delete(
                RoomsContract.ROOM_TABLE_NAME,
                "${RoomsContract.COLUMN_NAME_ROOM_ID} = ?",
                arrayOf(roomId.toString()),
            )
        }
    }

    fun drop() {
        dbHelper.writableDatabase.use { db ->
            db.execSQL("DELETE FROM ${RoomsContract.ROOM_TABLE_NAME}")
        }
    }
}
