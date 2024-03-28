package woowacourse.omok.omokdb

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import omok.model.position.Position

class OmokDbHelper(context: Context) : SQLiteOpenHelper(context, "omok.db", null, 2) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE ${OmokContract.TABLE_NAME} (\n" +
                "  ${OmokContract.COLUMN_ROW} char(1),\n" +
                "  ${OmokContract.COLUMN_COLUMN} int,\n" +
                "  ${OmokContract.COLUMN_COLOR} char(1)\n" +
                ")",
        )
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int,
    ) {
        db?.execSQL("DROP TABLE IF EXISTS ${OmokContract.TABLE_NAME}")
        onCreate(db)
    }

    fun insert(
        stoneRow: String,
        stoneColumn: Int,
        stoneColor: String,
    ) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("${OmokContract.COLUMN_ROW}", stoneRow)
        values.put("${OmokContract.COLUMN_COLUMN}", stoneColumn)
        values.put("${OmokContract.COLUMN_COLOR}", stoneColor)
        db.insert(OmokContract.TABLE_NAME, null, values)
    }

    @SuppressLint("Range")
    fun selectStonesInfo(): List<Pair<Position, String>> {
        val db = this.readableDatabase
        val dataList = mutableListOf<Pair<Position, String>>()
        db?.let {
            val cursor = it.rawQuery("SELECT * FROM ${OmokContract.TABLE_NAME}", null)

            while (cursor.moveToNext()) {
                val stoneRow = cursor.getString(cursor.getColumnIndex(OmokContract.COLUMN_ROW))
                val stoneColumn = cursor.getInt(cursor.getColumnIndex(OmokContract.COLUMN_COLUMN))
                val color = cursor.getString(cursor.getColumnIndex(OmokContract.COLUMN_COLOR))
                dataList.add(Pair(Position.of(stoneRow[0], stoneColumn), color))
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
