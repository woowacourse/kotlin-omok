package woowacourse.omok.model.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import androidx.core.content.contentValuesOf

class GameRoomDao(
    context: Context,
) : DatabaseDao<Room> {
    override val dbHelper: SQLiteOpenHelper = GameRoomDbHelper(context)
    private val writableDb: SQLiteDatabase by lazy { dbHelper.writableDatabase }

    override fun save(item: Room): Room {
        val roomId =
            writableDb.insert(
                GameRoomContract.TABLE_NAME,
                null,
                contentValuesOf(
                    GameRoomContract.COLUMN_TITLE to item.title,
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
            entries.add(Room(roomId, roomTitle))
        }

        cursor.close()

        return entries
    }

    override fun drop() {
        writableDb.delete(GameRoomContract.TABLE_NAME, null, null)
    }
}
