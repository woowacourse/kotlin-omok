package woowacourse.omok.database

import android.content.ContentValues

class OmokDao(private val dbHelper: OmokDbHelper) {
    fun insertData(omokEntity: OmokEntity) {
        val db = dbHelper.writableDatabase
        val values =
            ContentValues().apply {
                put(OmokContract.COLUMN_NAME_X, omokEntity.x)
                put(OmokContract.COLUMN_NAME_Y, omokEntity.y)
                put(OmokContract.COLUMN_NAME_COLOR, omokEntity.color)
                put(OmokContract.COLUMN_NAME_ROOM_NAME, omokEntity.roomName)
            }
        db.insert(OmokContract.TABLE_NAME, null, values)
    }

    fun queryByRoomName(roomName: String): List<OmokEntity> {
        val dbReader = dbHelper.readableDatabase
        val result = mutableListOf<OmokEntity>()
        dbReader.query(
            OmokContract.TABLE_NAME,
            null,
            "${OmokContract.COLUMN_NAME_ROOM_NAME} = ?",
            arrayOf(roomName),
            null,
            null,
            null,
        ).use { cursor ->
            while (cursor.moveToNext()) {
                val x: Int = cursor.getInt(cursor.getColumnIndexOrThrow(OmokContract.COLUMN_NAME_X))
                val y: Int = cursor.getInt(cursor.getColumnIndexOrThrow(OmokContract.COLUMN_NAME_Y))
                val color: String = cursor.getString(cursor.getColumnIndexOrThrow(OmokContract.COLUMN_NAME_COLOR))
                result.add(OmokEntity(x, y, color, roomName))
            }
        }
        return result
    }

    fun queryRoomNames(): ArrayList<String> {
        val dbReader = dbHelper.readableDatabase
        val result = ArrayList<String>()

        dbReader.query(
            true,
            OmokContract.TABLE_NAME,
            arrayOf(OmokContract.COLUMN_NAME_ROOM_NAME),
            null,
            null,
            null,
            null,
            null,
            null,
        ).use { cursor ->
            while (cursor.moveToNext()) {
                val roomName: String = cursor.getString(cursor.getColumnIndexOrThrow(OmokContract.COLUMN_NAME_ROOM_NAME))
                if (roomName.isNotEmpty() && !result.contains(roomName)) {
                    result.add(roomName)
                }
            }
        }
        return result
    }

    fun clearAll() {
        dbHelper.writableDatabase.delete(
            OmokContract.TABLE_NAME,
            null,
            null,
        )
    }

    fun clearRoom(roomName: String) {
        dbHelper.writableDatabase.delete(
            OmokContract.TABLE_NAME,
            "${OmokContract.COLUMN_NAME_ROOM_NAME} = ?",
            arrayOf(roomName),
        )
    }

    fun close() {
        dbHelper.close()
    }
}
