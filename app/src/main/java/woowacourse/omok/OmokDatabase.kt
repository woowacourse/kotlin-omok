package woowacourse.omok

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import domain.State

class OmokDatabase(
    private val context: Context
) {

    private val db: SQLiteDatabase by lazy {
        OmokDbHelper(context).writableDatabase
    }

    fun fetchStones(): List<StoneEntity> {
        val stones: MutableList<StoneEntity> = mutableListOf()

        val cursor = getStonesCursor(db)

        while (cursor.moveToNext()) {
            val index = cursor.getInt(
                cursor.getColumnIndexOrThrow(OmokContract.TABLE_COLUMN_INDEX)
            )
            val color = cursor.getString(
                cursor.getColumnIndexOrThrow(OmokContract.TABLE_COLUMN_COLOR)
            )
            stones.add(StoneEntity(index, color))
        }
        cursor.close()

        return stones
    }

    private fun getStonesCursor(db: SQLiteDatabase): Cursor {
        return db.query(
            OmokContract.TABLE_NAME,
            arrayOf(
                OmokContract.TABLE_COLUMN_INDEX,
                OmokContract.TABLE_COLUMN_COLOR
            ),
            null,
            null,
            null,
            null,
            null
        )
    }

    fun saveStone(index: Int, state: State) {
        val values = ContentValues()
        values.put(OmokContract.TABLE_COLUMN_INDEX, index)
        values.put(OmokContract.TABLE_COLUMN_COLOR, state.name)

        db.insert(OmokContract.TABLE_NAME, null, values)
    }

    fun close() {
        db.close()
    }
}
