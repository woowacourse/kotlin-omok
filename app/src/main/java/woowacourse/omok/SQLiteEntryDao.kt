package woowacourse.omok

import android.content.ContentValues
import android.content.Context

interface EntryDao {
    fun save(entry: Entry): Entry
    fun findAll(): List<Entry>
}

class SQLiteEntryDao(context: Context?) : EntryDao {
    private val dbHelper = FeedReaderDbHelper(context)

    override fun save(entry: Entry): Entry {
        val db = dbHelper.readableDatabase
        val values = ContentValues().apply {
            put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, entry.title)
            put(FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE, entry.subtitle)
        }
        val id = db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values)
        if (id < 0) throw RuntimeException()
        return entry.copy(id = id)
    }

    override fun findAll(): List<Entry> {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            FeedReaderContract.FeedEntry.TABLE_NAME,
            arrayOf("_id", "title", "subtitle"),
            null,
            null,
            null,
            null,
            null
        )
        val entries: MutableList<Entry> = mutableListOf()
        while (cursor.moveToNext()) {
            entries.add(
                Entry(
                    cursor.getString(cursor.getColumnIndexOrThrow("title")),
                    cursor.getString(cursor.getColumnIndexOrThrow("subtitle")),
                    cursor.getLong(cursor.getColumnIndexOrThrow("_id"))
                )
            )
        }
        cursor.close()
        return entries
    }

    fun drop() {
        val db = dbHelper.writableDatabase
        db.delete(FeedReaderContract.FeedEntry.TABLE_NAME, null, null)
    }
}
