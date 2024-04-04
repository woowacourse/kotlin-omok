package woowacourse.omok.model.database

import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import androidx.core.content.contentValuesOf

class PlacementDao(
    context: Context,
) : DatabaseDao<Placement> {
    override val dbHelper: SQLiteOpenHelper = OmokDbHelper(context)

    override fun save(item: Placement): Placement {
        val db = dbHelper.writableDatabase
        val placementId =
            db.insert(
                PlacementContract.TABLE_NAME,
                null,
                contentValuesOf(
                    PlacementContract.COLUMN_ROOM_ID to item.gameId,
                    PlacementContract.COLUMN_COLOR to item.color,
                    PlacementContract.COLUMN_PLACEMENT_HORIZONTAL_COORDINATE to item.horizontalCoordinate,
                    PlacementContract.COLUMN_PLACEMENT_VERTICAL_COORDINATE to item.verticalCoordinate,
                ),
            )

        return item.copy(placementId = placementId)
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
                    PlacementContract.COLUMN_PLACEMENT_HORIZONTAL_COORDINATE,
                    PlacementContract.COLUMN_PLACEMENT_VERTICAL_COORDINATE,
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
            val placementHorizontalCoordinate =
                cursor.getInt(cursor.getColumnIndexOrThrow(PlacementContract.COLUMN_PLACEMENT_HORIZONTAL_COORDINATE))
            val placementVerticalCoordinate =
                cursor.getInt(cursor.getColumnIndexOrThrow(PlacementContract.COLUMN_PLACEMENT_VERTICAL_COORDINATE))
            entries.add(Placement(placementId, roomId, color, placementHorizontalCoordinate, placementVerticalCoordinate))
        }

        cursor.close()

        return entries
    }

    override fun drop() {
        val db = dbHelper.writableDatabase
        db.delete(PlacementContract.TABLE_NAME, null, null)
    }
}
