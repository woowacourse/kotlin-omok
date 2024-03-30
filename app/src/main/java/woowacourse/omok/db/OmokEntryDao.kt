package woowacourse.omok.db

import android.content.Context
import android.provider.BaseColumns
import androidx.core.content.contentValuesOf

class OmokEntryDao(context: Context) {
    private val dbHelper = OmokDbHelper(context)

    fun save(entry: OmokEntry): OmokEntry {
        val db = dbHelper.writableDatabase
        val id =
            db.insert(
                OmokContract.TABLE_NAME,
                null,
                contentValuesOf(
                    OmokContract.X to entry.x,
                    OmokContract.Y to entry.y,
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
                arrayOf(
                    BaseColumns._ID,
                    OmokContract.X,
                    OmokContract.Y,
                    OmokContract.COLOR,
                ),
                null,
                null,
                null,
                null,
                null,
            )
        val entries = mutableListOf<OmokEntry>()
        while (cursor.moveToNext()) {
            val id = cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID))
            val x = cursor.getInt(cursor.getColumnIndexOrThrow(OmokContract.X))
            val y = cursor.getInt(cursor.getColumnIndexOrThrow(OmokContract.Y))
            val color = cursor.getString(cursor.getColumnIndexOrThrow(OmokContract.COLOR))
            entries.add(OmokEntry(id, x, y, color))
        }
        cursor.close()
        return entries
    }

    fun drop() {
        val db = dbHelper.writableDatabase
        db.delete(OmokContract.TABLE_NAME, null, null)
    }
}
