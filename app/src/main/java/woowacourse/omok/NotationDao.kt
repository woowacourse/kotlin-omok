package woowacourse.omok

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import androidx.core.content.contentValuesOf
import woowacourse.omok.FeedReaderContract.FeedNotation
import woowacourse.omok.FeedReaderContract.SQL_DROP_TABLE
import woowacourse.omok.domain.omok.model.Place

class NotationDao(context: Context) {
    private val dbHelper = FeedReaderDbHelper(context)

    fun save(place: Place): Place {
        val db = dbHelper.writableDatabase
        val id =
            db.insert(
                FeedNotation.TABLE_NAME,
                null,
                contentValuesOf(
                    FeedNotation.COLUMN_NAME_COLOR to place.color,
                    FeedNotation.COLUMN_NAME_ROW_COORDINATE to place.rowCoordinate,
                    FeedNotation.COLUMN_NAME_COL_COORDINATE to place.colCoordinate,
                ),
            )
        return place.copy(id = id)
    }

    fun findAll(): List<Place> {
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
        val notation: MutableList<Place> = mutableListOf()
        addToNotation(cursor, notation)
        cursor.close()
        return notation
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

    private fun addToNotation(
        cursor: Cursor,
        notation: MutableList<Place>,
    ) {
        while (cursor.moveToNext()) {
            val id = cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID))
            val color =
                cursor.getString(cursor.getColumnIndexOrThrow(FeedNotation.COLUMN_NAME_COLOR))
            val rowCoordinate =
                cursor.getInt(cursor.getColumnIndexOrThrow(FeedNotation.COLUMN_NAME_ROW_COORDINATE))
            val colCoordinate =
                cursor.getInt(cursor.getColumnIndexOrThrow(FeedNotation.COLUMN_NAME_COL_COORDINATE))
            notation.add(Place(color, rowCoordinate, colCoordinate, id))
        }
    }

    fun delete() {
        val db = dbHelper.writableDatabase
        db.delete(FeedNotation.TABLE_NAME, null, null)
    }

    fun drop() {
        val db = dbHelper.writableDatabase
        db.execSQL(SQL_DROP_TABLE)
    }
}
