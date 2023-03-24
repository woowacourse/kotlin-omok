package woowacourse.omok.database

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase

class DBController(private val wDb: SQLiteDatabase) {
    fun insertDB(color: String, index: Int) {
        val values = ContentValues()
        values.put(OmokConstract.TABLE_COLUMN_COLOR, color)
        values.put(OmokConstract.TABLE_COLUMN_POSITION, index)
        wDb.insert(OmokConstract.TABLE_NAME, null, values)
    }

    fun checkDB(color: String): List<Int> {
        val result = mutableListOf<Int>()
        val cursor = wDb.rawQuery("SELECT * FROM ${OmokConstract.TABLE_NAME} WHERE ${OmokConstract.TABLE_COLUMN_COLOR} = '" + color + "';", null)
        with(cursor) {
            while (moveToNext()) { result.add(getInt(getColumnIndexOrThrow(OmokConstract.TABLE_COLUMN_POSITION))) }
        }
        cursor.close()
        return result.toList()
    }

    fun deleteDB() {
        wDb.execSQL("DELETE FROM ${OmokConstract.TABLE_NAME}")
    }
}
