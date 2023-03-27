package woowacourse.omok.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class OmokDBHelper(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE ${OmokConstract.TABLE_NAME} (" +
                "  ${OmokConstract.TABLE_COLUMN_POSITION} int not null," +
                "  ${OmokConstract.TABLE_COLUMN_COLOR} varchar(50) not null" +
                ");"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${OmokConstract.TABLE_NAME}")
        onCreate(db)
    }

    fun getIndexsByColor(db: SQLiteDatabase, color: String): List<Int> {
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

    fun insert(db: SQLiteDatabase, index: Int, color: String) {
        if (!db.isOpen) throw IllegalAccessException()
        db.insert(OmokConstract.TABLE_NAME, null, values(index, color))
    }

    private fun values(position: Int, color: String): ContentValues {
        val values = ContentValues()
        values.put(OmokConstract.TABLE_COLUMN_POSITION, position)
        values.put(OmokConstract.TABLE_COLUMN_COLOR, color)
        return values
    }

    fun deleteAll(db: SQLiteDatabase) {
        db.execSQL("DELETE FROM ${OmokConstract.TABLE_NAME}")
    }

    fun close(db: SQLiteDatabase) {
        db.close()
    }

    companion object {
        private val DATABASE_NAME = "omok"
    }
}
