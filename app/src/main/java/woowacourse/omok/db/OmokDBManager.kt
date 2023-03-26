package woowacourse.omok.db

import android.content.ContentValues
import android.content.Context

class OmokDBManager(context: Context) {
    private val db = OmokDBHelper(context).writableDatabase

    fun getIndexsByColor(color: String): List<Int> {
        val indexs = mutableListOf<Int>()
        if (!db.isOpen) throw IllegalAccessException()
        val cursor = db.query(
            OmokConstract.TABLE_NAME,
            arrayOf(OmokConstract.TABLE_COLUMN_POSITION),
            "${OmokConstract.TABLE_COLUMN_COLOR} = ?",
            arrayOf(color),
            null,
            null,
            null
        )

        while (cursor.moveToNext()) indexs.add(cursor.getInt(0))
        return indexs.toList()
    }

    fun insert(index: Int, color: String) {
        if (!db.isOpen) throw IllegalAccessException()
        db.insert(OmokConstract.TABLE_NAME, null, values(index, color))
    }

    private fun values(position: Int, color: String): ContentValues {
        val values = ContentValues()
        values.put(OmokConstract.TABLE_COLUMN_POSITION, position)
        values.put(OmokConstract.TABLE_COLUMN_COLOR, color)
        return values
    }

    fun deleteAll() {
        db.execSQL("DELETE FROM ${OmokConstract.TABLE_NAME}")
    }

    fun close() {
        db.close()
    }
}
