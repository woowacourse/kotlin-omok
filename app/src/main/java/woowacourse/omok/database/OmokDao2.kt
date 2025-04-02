package woowacourse.omok.database

import android.content.ContentValues

class OmokDao2(private val dbHelper: OmokDbHelper) {
    fun insertData(omokEntity: OmokEntity2) {
        val db = dbHelper.writableDatabase
        val values =
            ContentValues().apply {
                put(OmokContract2.COLUMN_NAME_X, omokEntity.x)
                put(OmokContract2.COLUMN_NAME_Y, omokEntity.y)
                put(OmokContract2.COLUMN_NAME_COLOR, omokEntity.color)
                put(OmokContract2.COLUMN_NAME_ROOM_NAME, omokEntity.roomName)
            }
        db.insert(OmokContract2.TABLE_NAME, null, values)
    }

    fun queryAll(): List<OmokEntity2> {
        val dbReader = dbHelper.readableDatabase
        val result = mutableListOf<OmokEntity2>()
        dbReader.rawQuery("SELECT * FROM ${OmokContract2.TABLE_NAME}", null).use { cursor ->
            while (cursor.moveToNext()) {
                val x: Int = cursor.getInt(cursor.getColumnIndexOrThrow(OmokContract2.COLUMN_NAME_X))
                val y: Int = cursor.getInt(cursor.getColumnIndexOrThrow(OmokContract2.COLUMN_NAME_Y))
                val color: String = cursor.getString(cursor.getColumnIndexOrThrow(OmokContract2.COLUMN_NAME_COLOR))
                val roomName: String = cursor.getString(cursor.getColumnIndexOrThrow(OmokContract2.COLUMN_NAME_ROOM_NAME))
                result.add(OmokEntity2(x, y, color, roomName))
            }
        }
        return result
    }

    fun clear() {
        dbHelper.writableDatabase.delete(OmokContract2.TABLE_NAME, null, null)
    }

    fun close() {
        dbHelper.close()
    }
}
