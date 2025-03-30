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
            }
        db.insert(OmokContract.TABLE_NAME, null, values)
    }

    fun queryAll(): List<OmokEntity> {
        val dbReader = dbHelper.readableDatabase
        val result = mutableListOf<OmokEntity>()
        dbReader.rawQuery("SELECT * FROM ${OmokContract.TABLE_NAME}", null).use { cursor ->
            while (cursor.moveToNext()) {
                val x: Int = cursor.getInt(cursor.getColumnIndexOrThrow(OmokContract.COLUMN_NAME_X))
                val y: Int = cursor.getInt(cursor.getColumnIndexOrThrow(OmokContract.COLUMN_NAME_Y))
                val color: String = cursor.getString(cursor.getColumnIndexOrThrow(OmokContract.COLUMN_NAME_COLOR))
                result.add(OmokEntity(x, y, color))
            }
        }
        return result
    }

    fun clear() {
        dbHelper.writableDatabase.delete(OmokContract.TABLE_NAME, null, null)
    }

    fun close() {
        dbHelper.close()
    }
}
