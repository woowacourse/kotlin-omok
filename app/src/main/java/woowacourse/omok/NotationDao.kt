package woowacourse.omok

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import androidx.core.content.contentValuesOf
import woowacourse.omok.FeedReaderContract.FeedNotation

class NotationDao(private val context: Context) {
    private val dbHelper = FeedReaderDbHelper(context)

    fun save(notation: Notation): Notation {
        val db = dbHelper.writableDatabase
        val id =
            db.insert(
                FeedNotation.TABLE_NAME,
                null,
                contentValuesOf(
                    FeedNotation.COLUMN_NAME_COLOR to notation.color,
                    FeedNotation.COLUMN_NAME_ROW_COORDINATE to notation.rowCoordinate,
                    FeedNotation.COLUMN_NAME_COL_COORDINATE to notation.colCoordinate,
                ),
            )
        return notation.copy(id = id)
    }

    fun findAll(): List<Notation> {
        val db = dbHelper.readableDatabase
        val cursor =
            db.query(
                FeedNotation.TABLE_NAME,
                arrayOf(
                    BaseColumns._ID,
                    FeedNotation.COLUMN_NAME_COLOR,
                    FeedNotation.COLUMN_NAME_ROW_COORDINATE,
                    FeedNotation.COLUMN_NAME_COL_COORDINATE,
                ),
            )
        val notation: MutableList<Notation> = mutableListOf()
        addToNotation(cursor, notation)
        cursor.close()
        return notation
    }

    private fun addToNotation(
        cursor: Cursor,
        notation: MutableList<Notation>,
    ) {
        while (cursor.moveToNext()) {
            val id = cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID))
            val color =
                cursor.getString(cursor.getColumnIndexOrThrow(FeedNotation.COLUMN_NAME_COLOR))
            val rowCoordinate =
                cursor.getInt(cursor.getColumnIndexOrThrow(FeedNotation.COLUMN_NAME_ROW_COORDINATE))
            val colCoordinate =
                cursor.getInt(cursor.getColumnIndexOrThrow(FeedNotation.COLUMN_NAME_COL_COORDINATE))
            notation.add(Notation(color, rowCoordinate, colCoordinate, id))
        }
    }

    private fun SQLiteDatabase.query(
        table: String,
        columns: Array<String>,
    ): Cursor {
        return query(
            table,
            columns,
            null,
            null,
            null,
            null,
            null,
        )
    }

    fun drop() {
        val db = dbHelper.writableDatabase
        db.delete(FeedNotation.TABLE_NAME, null, null)
    }
}
