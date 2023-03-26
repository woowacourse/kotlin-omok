package woowacourse.omok.database

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase

class DBController(private val writableDb: SQLiteDatabase) {
    fun insertDB(color: String, index: Int) {
        val values = ContentValues()
        values.put(OmokConstract.TABLE_COLUMN_COLOR, color)
        values.put(OmokConstract.TABLE_COLUMN_POSITION, index)
        writableDb.insert(OmokConstract.TABLE_NAME, null, values)
    }

    fun getIndex(color: String): List<Int> {
        val result = mutableListOf<Int>()
        val cursor = writableDb.rawQuery("SELECT * FROM ${OmokConstract.TABLE_NAME} WHERE ${OmokConstract.TABLE_COLUMN_COLOR} = '" + color + "';", null)
        with(cursor) {
            while (moveToNext()) { result.add(getInt(getColumnIndexOrThrow(OmokConstract.TABLE_COLUMN_POSITION))) }
        }
        cursor.close()
        return result.toList()
    }

    fun deleteDB() {
        writableDb.execSQL("DELETE FROM ${OmokConstract.TABLE_NAME}")
    }
}
