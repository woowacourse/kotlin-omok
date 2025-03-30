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

    fun insertOmok(
        row: Int,
        col: Int,
        stoneColor: String,
    ) {
        dbHelper.writableDatabase.use { db ->
            val values =
                ContentValues().apply {
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

    fun hasOmokData(): Boolean =
        dbHelper.readableDatabase.use { db ->
            val query = "SELECT EXISTS (SELECT 1 FROM ${OmokContract.TABLE_NAME} LIMIT 1)"
            db.rawQuery(query, null).use { cursor ->
                cursor.moveToFirst() && cursor.getInt(0) == 1
            }
        }

    fun getAllStones(): List<StoneEntity> =
        dbHelper.readableDatabase.use { db ->
            val result = mutableListOf<StoneEntity>()

            db
                .query(
                    OmokContract.TABLE_NAME,
                    arrayOf(
                        OmokContract.COLUMN_ROW_POSITION,
                        OmokContract.COLUMN_COL_POSITION,
                        OmokContract.COLUMN_STONE_COLOR,
                    ),
                    null,
                    null,
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
                        result.add(StoneEntity(row, col, stoneColor))
                    }
                }

            result
        }
}
