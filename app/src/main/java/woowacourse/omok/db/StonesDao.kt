package woowacourse.omok.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import woowacourse.omok.db.OmokContract.Entry

class StonesDao(context: Context) {
    private val dbHelper = OmokDbHelper(context)

    fun getAllStones(): List<StoneEntity> {
        val db = dbHelper.readableDatabase
        val cursor =
            db.query(
                Entry.TABLE_NAME,
                arrayOf(
                    BaseColumns._ID,
                    Entry.COLUMN_STONE_COLOR,
                    Entry.COLUMN_STONE_ROW,
                    Entry.COLUMN_STONE_COL,
                    Entry.COLUMN_STONE_ORDER,
                ),
                Entry.COLUMN_STONE_ORDER,
            )
        val entries: MutableList<StoneEntity> = mutableListOf()
        while (cursor.moveToNext()) {
            val id = cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID))
            val color = cursor.getString(cursor.getColumnIndexOrThrow(Entry.COLUMN_STONE_COLOR))
            val row = cursor.getInt(cursor.getColumnIndexOrThrow(Entry.COLUMN_STONE_ROW))
            val column = cursor.getInt(cursor.getColumnIndexOrThrow(Entry.COLUMN_STONE_COL))
            val order = cursor.getInt(cursor.getColumnIndexOrThrow(Entry.COLUMN_STONE_ORDER))
            entries.add(StoneEntity(id, color, row, column, order))
        }
        cursor.close()
        return entries
    }

    fun insertStone(stoneEntity: StoneEntity): StoneEntity {
        val db = dbHelper.writableDatabase
        val values =
            ContentValues().apply {
                put(Entry.COLUMN_STONE_COLOR, stoneEntity.color)
                put(Entry.COLUMN_STONE_ROW, stoneEntity.row)
                put(Entry.COLUMN_STONE_COL, stoneEntity.column)
                put(Entry.COLUMN_STONE_ORDER, stoneEntity.order)
            }
        val insertedId = db.insert(Entry.TABLE_NAME, null, values)

        return stoneEntity.copy(id = insertedId)
    }

    fun deleteAllStones() {
        val db = dbHelper.writableDatabase
        db.delete(
            Entry.TABLE_NAME,
            null,
            null,
        )
    }

    private fun SQLiteDatabase.query(
        table: String,
        columns: Array<String>,
        orderBy: String,
    ): Cursor {
        return query(table, columns, null, null, null, null, orderBy)
    }
}
