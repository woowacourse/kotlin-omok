package woowacourse.omok.model.database

import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import androidx.core.content.contentValuesOf

class GameRoomDao(
    context: Context,
) : DatabaseDao<Room> {
    override val dbHelper: SQLiteOpenHelper = GameRoomDbHelper(context)

    override fun save(item: Room): Room {
        val db = dbHelper.writableDatabase
        val roomId =
            db.insert(
                GameRoomContract.TABLE_NAME,
                null,
                contentValuesOf(
                    GameRoomContract.COLUMN_TITLE to item.title,
                    GameRoomContract.COLUMN_STATUS to item.status,
                ),
            )

        return item.copy(id = roomId)
    }

    fun findAll(): List<Room> {
        val db = dbHelper.readableDatabase
        val cursor =
            db.query(
                GameRoomContract.TABLE_NAME,
                arrayOf(
                    BaseColumns._ID,
                    GameRoomContract.COLUMN_TITLE,
                    GameRoomContract.COLUMN_STATUS,
                ),
                null,
                null,
                null,
                null,
                null,
            )

        val entries = mutableListOf<Room>()
        while (cursor.moveToNext()) {
            val roomId = cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID))
            val roomTitle = cursor.getString(cursor.getColumnIndexOrThrow(GameRoomContract.COLUMN_TITLE))
            val roomStatus = cursor.getString(cursor.getColumnIndexOrThrow(GameRoomContract.COLUMN_STATUS))
            entries.add(Room(roomId, roomTitle, roomStatus))
        }

        cursor.close()

        return entries
    }

    override fun drop() {
        val db = dbHelper.writableDatabase
        db.delete(GameRoomContract.TABLE_NAME, null, null)
    }
}
