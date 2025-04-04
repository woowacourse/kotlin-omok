package woowacourse.omok.data.dao

import android.content.ContentValues
import android.content.Context
import android.util.Log
import woowacourse.omok.data.DbHelper
import woowacourse.omok.data.OmokContract
import woowacourse.omok.data.model.StoneEntity

class OmokDao(
    context: Context,
) {
    val dbHelper: DbHelper = DbHelper(context)

    fun deleteDatabase() {
        dbHelper.writableDatabase.use { db ->
            db.delete(OmokContract.TABLE_NAME, null, null)
        }
    }

    fun getAllRooms(): List<Int> =
        dbHelper.readableDatabase.use { db ->
            val roomIds = mutableListOf<Int>()
            val query =
                "SELECT DISTINCT ${OmokContract.COLUMN_ROOM_ID} FROM ${OmokContract.TABLE_NAME}"
            db.rawQuery(query, null).use { cursor ->
                while (cursor.moveToNext()) {
                    roomIds.add(cursor.getInt(0))
                }
            }
            roomIds
        }

    fun insertOmok(
        roomId: Int,
        row: Int,
        col: Int,
        stoneColor: String,
    ) {
        dbHelper.writableDatabase.use { db ->
            val values =
                ContentValues().apply {
                    put(OmokContract.COLUMN_ROOM_ID, roomId)
                    put(OmokContract.COLUMN_ROW_POSITION, row)
                    put(OmokContract.COLUMN_COL_POSITION, col)
                    put(OmokContract.COLUMN_STONE_COLOR, stoneColor)
                }

            val newRowId = db.insert(OmokContract.TABLE_NAME, null, values)
            if (newRowId == -1L) {
                Log.e("MainActivity", "insert failed")
            } else {
                Log.d("MainActivity", "insert success: $newRowId")
            }
        }
    }

    fun insertRoom(roomId: Int) {
        val values =
            ContentValues().apply {
                put(OmokContract.COLUMN_ROOM_ID, roomId)
            }
        dbHelper.writableDatabase.insert(OmokContract.TABLE_NAME, null, values)
    }

    fun hasOmokData(roomId: Int): Boolean =
        dbHelper.readableDatabase.use { db ->
            val query =
                "SELECT EXISTS (SELECT 1 FROM ${OmokContract.TABLE_NAME} WHERE ${OmokContract.COLUMN_ROOM_ID} = ? LIMIT 1)"
            db.rawQuery(query, arrayOf(roomId.toString())).use { cursor ->
                cursor.moveToFirst() && cursor.getInt(0) == 1
            }
        }

    fun getStonesByRoomId(roomId: Int): List<StoneEntity> =
        dbHelper.readableDatabase.use { db ->
            val result = mutableListOf<StoneEntity>()

            val selection =
                "${OmokContract.COLUMN_ROOM_ID} = ? AND ${OmokContract.COLUMN_ROW_POSITION} IS NOT NULL"
            val selectionArgs = arrayOf(roomId.toString())

            db
                .query(
                    OmokContract.TABLE_NAME,
                    arrayOf(
                        OmokContract.COLUMN_ROOM_ID,
                        OmokContract.COLUMN_ROW_POSITION,
                        OmokContract.COLUMN_COL_POSITION,
                        OmokContract.COLUMN_STONE_COLOR,
                    ),
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null,
                ).use { cursor ->
                    while (cursor.moveToNext()) {
                        val row =
                            cursor.getInt(cursor.getColumnIndexOrThrow(OmokContract.COLUMN_ROW_POSITION))
                        val col =
                            cursor.getInt(cursor.getColumnIndexOrThrow(OmokContract.COLUMN_COL_POSITION))
                        val stoneColor =
                            cursor.getString(cursor.getColumnIndexOrThrow(OmokContract.COLUMN_STONE_COLOR))
                        result.add(StoneEntity(roomId, row, col, stoneColor))
                    }
                }

            result
        }
}
