package woowacourse.omok.data.datasource

import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf
import woowacourse.omok.data.db.OmokEntity
import woowacourse.omok.data.db.OmokSchema.OmokContract.COLUMN_NAME_BOARD_COLUMN
import woowacourse.omok.data.db.OmokSchema.OmokContract.COLUMN_NAME_BOARD_ROW
import woowacourse.omok.data.db.OmokSchema.OmokContract.COLUMN_NAME_STONE
import woowacourse.omok.data.db.OmokSchema.OmokContract.TABLE_NAME

class OmokDataSource(private val dbHelper: SQLiteOpenHelper) {
    fun save(entity: OmokEntity) {
        dbHelper.writableDatabase.use { db ->
            val values =
                contentValuesOf(
                    COLUMN_NAME_BOARD_COLUMN to entity.column,
                    COLUMN_NAME_BOARD_ROW to entity.row,
                    COLUMN_NAME_STONE to entity.stone,
                )

            db.insert(TABLE_NAME, null, values)
        }
    }

    fun readAll(): List<OmokEntity> {
        val reader = dbHelper.readableDatabase
        val entries = mutableListOf<OmokEntity>()
        val cursor =
            reader.query(
                TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null,
            )

        cursor.use {
            while (it.moveToNext()) {
                val column = it.getInt(it.getColumnIndexOrThrow(COLUMN_NAME_BOARD_COLUMN))
                val row = it.getInt(it.getColumnIndexOrThrow(COLUMN_NAME_BOARD_ROW))
                val stone = it.getString(it.getColumnIndexOrThrow(COLUMN_NAME_STONE))

                entries.add(OmokEntity(row, column, stone))
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
