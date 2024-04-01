package woowacourse.omok.game.database

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.core.content.contentValuesOf

class OmokEntryDao(private val context: Context) {
    private val dbHelper = OmokDbHelper(context)

    fun save(entry: OmokEntry): OmokEntry {
        val db = dbHelper.writableDatabase
        val id =
            db.insert(
                OmokContract.TABLE_NAME,
                null,
                contentValuesOf(
                    OmokContract.ROW to entry.row,
                    OmokContract.COL to entry.col,
                    OmokContract.COLOR to entry.color,
                ),
            )
        return entry.copy(id = id)
    }

    fun findAll(): List<OmokEntry> {
        val db = dbHelper.readableDatabase
        val cursor =
            db.query(
                OmokContract.TABLE_NAME,
                arrayOf(OmokContract.ID, OmokContract.ROW, OmokContract.COL, OmokContract.COLOR),
            )

        val entries = mutableListOf<OmokEntry>()

        while (cursor.moveToNext()) {
            val id = cursor.getLong(cursor.getColumnIndexOrThrow(OmokContract.ID))
            val row = cursor.getInt(cursor.getColumnIndexOrThrow(OmokContract.ROW))
            val col = cursor.getInt(cursor.getColumnIndexOrThrow(OmokContract.COL))
            val color = cursor.getString(cursor.getColumnIndexOrThrow(OmokContract.COLOR))
            entries.add(OmokEntry(id, row, col, color))
        }

        cursor.close()

        return entries
    }

    private fun SQLiteDatabase.query(
        table: String,
        columns: Array<String>,
    ): Cursor {
        return query(table, columns, null, null, null, null, null)
    }

    fun drop() {
        val db = dbHelper.writableDatabase
        db.delete(OmokContract.TABLE_NAME, null, null)
    }
}
