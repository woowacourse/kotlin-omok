package woowacourse.omok.omokdb

import android.content.Context
import android.database.Cursor
import android.provider.BaseColumns
import androidx.core.content.contentValuesOf

class OmokDao(private val context: Context) {
    private val dbHelper = OmokDbHelper(context)

    fun insert(omokEntry: OmokEntry): OmokEntry {
        val db = dbHelper.writableDatabase
        val id =
            db.insert(
                OmokContract.TABLE_NAME,
                null,
                contentValuesOf(
                    OmokContract.COLUMN_ROW to omokEntry.row,
                    OmokContract.COLUMN_COLUMN to omokEntry.column,
                    OmokContract.COLUMN_COLOR to omokEntry.color,
                ),
            )
        return omokEntry.copy(id = id)
    }

    fun findAll(): List<OmokEntry> {
        val db = dbHelper.readableDatabase
        val cursor =
            db.query(
                OmokContract.TABLE_NAME,
                arrayOf(
                    OmokContract.COLUMN_ROW,
                    OmokContract.COLUMN_COLUMN,
                    OmokContract.COLUMN_COLOR,
                    BaseColumns._ID,
                ),
                null,
                null,
                null,
                null,
                null,
            )
        return omokEntries(cursor)
    }

    private fun omokEntries(cursor: Cursor): MutableList<OmokEntry> {
        val omokEntries: MutableList<OmokEntry> = mutableListOf()

        while (cursor.moveToNext()) {
            val id = cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID))
            val row = cursor.getString(cursor.getColumnIndexOrThrow(OmokContract.COLUMN_ROW))
            val column = cursor.getInt(cursor.getColumnIndexOrThrow(OmokContract.COLUMN_COLUMN))
            val color = cursor.getString(cursor.getColumnIndexOrThrow(OmokContract.COLUMN_COLOR))
            omokEntries.add(OmokEntry(row, column, color, id))
        }
        cursor.close()
        return omokEntries
    }

    fun reset() {
        val db = dbHelper.writableDatabase
        db.delete(OmokContract.TABLE_NAME, null, null)
    }
}
