package woowacourse.omok

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class OmokDbHelper(context: Context) : SQLiteOpenHelper(context, "omok.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE ${OmokContract.TABLE_NAME} (\n" +
                "  ${OmokContract.COLUMN_INDEX} int,\n" +
                "  ${OmokContract.COLUMN_COLOR} char(1)\n" +
                ")",
        )
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int,
    ) {
    }

    fun insert(
        index: Int,
        stoneColor: String,
    ) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("${OmokContract.COLUMN_INDEX}", index)
        values.put("${OmokContract.COLUMN_COLOR}", stoneColor)
        db.insert(OmokContract.TABLE_NAME, null, values)
    }

    @SuppressLint("Range")
    fun selectStonesInfo(): List<Pair<Int, String>> {
        val db = this.readableDatabase
        val dataList = mutableListOf<Pair<Int, String>>()
        db?.let {
            val cursor = it.rawQuery("SELECT * FROM ${OmokContract.TABLE_NAME}", null)

            while (cursor.moveToNext()) {
                val index = cursor.getInt(cursor.getColumnIndex(OmokContract.COLUMN_INDEX))
                val color = cursor.getString(cursor.getColumnIndex(OmokContract.COLUMN_COLOR))
                dataList.add(Pair(index, color))
            }
            cursor.close()
        }

        return dataList
    }

    fun reset() {
        val db = this.writableDatabase
        db?.execSQL("DELETE FROM ${OmokContract.TABLE_NAME}")
    }
}
