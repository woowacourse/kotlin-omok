package woowacourse.omok.data.dao

import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf
import woowacourse.omok.data.db.omok.OmokEntity
import woowacourse.omok.data.db.omok.OmokSchema.OmokContract.COLUMN_NAME_BOARD_COLUMN
import woowacourse.omok.data.db.omok.OmokSchema.OmokContract.COLUMN_NAME_BOARD_ROW
import woowacourse.omok.data.db.omok.OmokSchema.OmokContract.COLUMN_NAME_ROOM_ID
import woowacourse.omok.data.db.omok.OmokSchema.OmokContract.COLUMN_NAME_STONE
import woowacourse.omok.data.db.omok.OmokSchema.OmokContract.TABLE_NAME

class OmokDao(private val dbHelper: SQLiteOpenHelper) {
    fun save(entity: OmokEntity) {
        dbHelper.writableDatabase.use { db ->
            val values =
                contentValuesOf(
                    COLUMN_NAME_ROOM_ID to entity.roomId,
                    COLUMN_NAME_BOARD_COLUMN to entity.column,
                    COLUMN_NAME_BOARD_ROW to entity.row,
                    COLUMN_NAME_STONE to entity.stone,
                )

            db.insert(TABLE_NAME, null, values)
        }
    }

    fun readByRoomId(id: Long): List<OmokEntity> {
        val entries = mutableListOf<OmokEntity>()
        dbHelper.readableDatabase.use { reader ->
            val cursor =
                reader.query(
                    TABLE_NAME,
                    null,
                    "$COLUMN_NAME_ROOM_ID = ?",
                    arrayOf(id.toString()),
                    null,
                    null,
                    null,
                )

            cursor.use {
                while (it.moveToNext()) {
                    val roomId = it.getLong(it.getColumnIndexOrThrow(COLUMN_NAME_ROOM_ID))
                    val column = it.getInt(it.getColumnIndexOrThrow(COLUMN_NAME_BOARD_COLUMN))
                    val row = it.getInt(it.getColumnIndexOrThrow(COLUMN_NAME_BOARD_ROW))
                    val stone = it.getString(it.getColumnIndexOrThrow(COLUMN_NAME_STONE))
                    entries.add(OmokEntity(roomId, row, column, stone))
                }
            }
        }
        return entries
    }

    fun drop() {
        dbHelper.writableDatabase.use { db ->
            db.execSQL("DELETE FROM $TABLE_NAME")
        }
    }
}
