package woowacourse.omok.repository

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import domain.stone.Stone

class StoneRepository(context: Context) {
    private val db = StoneDbHelper(context).writableDatabase

    fun addStone(stone: Stone) {
        val values = ContentValues().apply {
            put(StoneEntry.COLUMN_NAME_X, stone.x.toString())
            put(StoneEntry.COLUMN_NAME_Y, stone.y)
        }

        db.insert(StoneEntry.TABLE_NAME, null, values)
    }

    fun readAll(): Set<Stone> {
        val projection = arrayOf(StoneEntry.COLUMN_NAME_X, StoneEntry.COLUMN_NAME_Y)
        val sortOrder = BaseColumns._ID

        val cursor = db.query(StoneEntry.TABLE_NAME, projection, null, null, null, null, sortOrder)
        val stones = mutableSetOf<Stone>()
        with(cursor) {
            while (moveToNext()) {
                val x = getString(getColumnIndexOrThrow(StoneEntry.COLUMN_NAME_X))
                val y = getInt(getColumnIndexOrThrow(StoneEntry.COLUMN_NAME_Y))

                stones.add(Stone(x[0], y))
            }
        }
        cursor.close()
        return stones
    }

    fun deleteAll() {
        db.delete(StoneEntry.TABLE_NAME, null, null)
    }

    fun close() {
        db.close()
    }
}