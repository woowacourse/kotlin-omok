package woowacourse.omok.model.database

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import androidx.core.content.contentValuesOf

interface DatabaseDao {
    val dbHelper: SQLiteOpenHelper
}

data class Placement(
    val placementId: Long = 0L,
    val gameId: Long,
    val color: String?,
    val index: Int,
)

class PlacementDao(
    context: Context,
) : DatabaseDao {
    override val dbHelper: SQLiteOpenHelper = PlacementDbHelper(context)

    fun save(placement: Placement): Placement {
        val db = dbHelper.writableDatabase
        val placementId =
            db.insert(
                PlacementContract.TABLE_NAME,
                null,
                contentValuesOf(
                    PlacementContract.COLUMN_ROOM_ID to placement.gameId,
                    PlacementContract.COLUMN_COLOR to placement.color,
                    PlacementContract.COLUMN_PLACEMENT_INDEX to placement.index,
                ),
            )

        return placement.copy(placementId = placementId)
    }

    fun findAll(gameId: Long): List<Placement> {
        val db = dbHelper.readableDatabase
        val cursor =
            db.query(
                PlacementContract.TABLE_NAME,
                arrayOf(
                    BaseColumns._ID,
                    PlacementContract.COLUMN_ROOM_ID,
                    PlacementContract.COLUMN_COLOR,
                    PlacementContract.COLUMN_PLACEMENT_INDEX,
                ),
                "${PlacementContract.COLUMN_ROOM_ID} = ?",
                arrayOf(gameId.toString()),
                null,
                null,
                null,
            )

        val entries = mutableListOf<Placement>()

        while (cursor.moveToNext()) {
            val placementId = cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID))
            val roomId =
                cursor.getLong(cursor.getColumnIndexOrThrow(PlacementContract.COLUMN_ROOM_ID))
            val color =
                cursor.getString(cursor.getColumnIndexOrThrow(PlacementContract.COLUMN_COLOR))
            val placementIndex =
                cursor.getInt(cursor.getColumnIndexOrThrow(PlacementContract.COLUMN_PLACEMENT_INDEX))
            entries.add(Placement(placementId, roomId, color, placementIndex))
        }

        cursor.close()

        return entries
    }

    private fun SQLiteDatabase.query(
        table: String,
        columns: Array<String>,
    ): Cursor {
        return query(table, columns, null, null, null, null, null)
    }

    fun drop() {
        val db = dbHelper.writableDatabase
        db.delete(PlacementContract.TABLE_NAME, null, null)
    }
}
