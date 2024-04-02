package woowacourse.omok

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import androidx.core.content.contentValuesOf
import woowacourse.omok.OmokDbContract.Places
import woowacourse.omok.OmokDbContract.SQL_DROP_TABLE
import woowacourse.omok.domain.omok.model.Place

class PlaceDao(context: Context) {
    private val dbHelper = OmokDbHelper(context)

    fun save(place: Place): Place {
        val db = dbHelper.writableDatabase
        val id =
            db.insert(
                Places.TABLE_NAME,
                null,
                contentValuesOf(
                    Places.COLUMN_NAME_COLOR to place.color,
                    Places.COLUMN_NAME_ROW_COORDINATE to place.rowCoordinate,
                    Places.COLUMN_NAME_COL_COORDINATE to place.colCoordinate,
                ),
            )
        return place.copy(id = id)
    }

    fun findAll(): List<Place> {
        val db = dbHelper.readableDatabase
        val cursor =
            db.query(
                Places.TABLE_NAME,
                arrayOf(
                    BaseColumns._ID,
                    Places.COLUMN_NAME_COLOR,
                    Places.COLUMN_NAME_ROW_COORDINATE,
                    Places.COLUMN_NAME_COL_COORDINATE,
                ),
            )
        val places: MutableList<Place> = mutableListOf()
        addToNotation(cursor, places)
        cursor.close()
        return places
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

    fun findById(id: Long): Place {
        val places = findAll()
        return places.first { it.id == id }
    }

    private fun addToNotation(
        cursor: Cursor,
        places: MutableList<Place>,
    ) {
        while (cursor.moveToNext()) {
            val id = cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID))
            val color =
                cursor.getString(cursor.getColumnIndexOrThrow(Places.COLUMN_NAME_COLOR))
            val rowCoordinate =
                cursor.getInt(cursor.getColumnIndexOrThrow(Places.COLUMN_NAME_ROW_COORDINATE))
            val colCoordinate =
                cursor.getInt(cursor.getColumnIndexOrThrow(Places.COLUMN_NAME_COL_COORDINATE))
            places.add(Place(color, rowCoordinate, colCoordinate, id))
        }
    }

    fun delete() {
        val db = dbHelper.writableDatabase
        db.delete(Places.TABLE_NAME, null, null)
    }

    fun drop() {
        val db = dbHelper.writableDatabase
        db.execSQL(SQL_DROP_TABLE)
    }
}
