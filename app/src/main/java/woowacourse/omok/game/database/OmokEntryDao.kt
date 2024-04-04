package woowacourse.omok.game.database

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.core.content.contentValuesOf
import woowacourse.omok.game.model.Color
import woowacourse.omok.game.model.Point
import woowacourse.omok.game.model.Stone
import woowacourse.omok.game.model.Stones

class OmokEntryDao(private val context: Context) {
    private val dbHelper = OmokDbHelper(context)

    fun save(entry: OmokEntry): OmokEntry {
        val db = dbHelper.writableDatabase
        db.insert(
            OmokContract.TABLE_NAME,
            null,
            contentValuesOf(
                OmokContract.INDEX to entry.index,
                OmokContract.COLOR to entry.color,
            ),
        )
        return entry.copy()
    }

    fun findAll(): List<OmokEntry> {
        val db = dbHelper.readableDatabase
        val cursor =
            db.query(
                OmokContract.TABLE_NAME,
                arrayOf(OmokContract.INDEX, OmokContract.COLOR),
            )

        val entries = mutableListOf<OmokEntry>()

        while (cursor.moveToNext()) {
            val index = cursor.getInt(cursor.getColumnIndexOrThrow(OmokContract.INDEX))
            val color = cursor.getString(cursor.getColumnIndexOrThrow(OmokContract.COLOR))
            entries.add(OmokEntry(index, color))
        }

        cursor.close()

        return entries
    }

    fun fetchStones(): Stones {
        val entries = findAll()
        val stones = Stones()

        entries.forEach { entry ->
            stones.add(Stone(Point.from(entry.index), stringToColor(entry.color)))
        }
        return stones
    }

    private fun stringToColor(color: String): Color = if (color == "흑") Color.BLACK else Color.WHITE

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
