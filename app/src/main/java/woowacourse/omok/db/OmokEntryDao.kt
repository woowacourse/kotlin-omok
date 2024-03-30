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
                    OmokContract.STONE_TYPE to entry.stoneType,
                    OmokContract.POSITION to entry.position,
                ),
            )
        return entry.copy(id = id)
    }

    fun findAll(): List<OmokEntry> {
        val db = dbHelper.readableDatabase
        val entries: MutableList<OmokEntry> = mutableListOf()
        val cursor =
            db.query(
                OmokContract.TABLE_NAME,
                arrayOf(BaseColumns._ID, OmokContract.STONE_TYPE, OmokContract.POSITION),
                null,
                null,
                null,
                null,
                null,
            )

        while (cursor.moveToNext()) {
            val id = cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID))
            val stoneType = cursor.getString(cursor.getColumnIndexOrThrow(OmokContract.STONE_TYPE))
            val position = cursor.getInt(cursor.getColumnIndexOrThrow(OmokContract.POSITION))
            entries.add(OmokEntry(stoneType, position, id))
        }
        cursor.close()
        return entries
    }

    fun drop() {
        val db = dbHelper.writableDatabase
        db.delete(OmokContract.TABLE_NAME, null, null)
    }
}
