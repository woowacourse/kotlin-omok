package woowacourse.omok.domain.model.database

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf

class OmokTurnDao(private val sqLiteOpenHelper: SQLiteOpenHelper) {
    fun createTable() {
        val db = sqLiteOpenHelper.writableDatabase
        db.execSQL(OmokTurnContract.CREATE_OMOK_TURN_TABLE)
    }

    fun save(omokTurn: OmokTurn): OmokTurn {
        val db = sqLiteOpenHelper.writableDatabase
        val id =
            db.insert(
                OmokTurnContract.TABLE_NAME,
                null,
                contentValuesOf(
                    OmokTurnContract.COLUMN_POSITION_ROW to omokTurn.row,
                    OmokTurnContract.COLUMN_POSITION_COLUMN to omokTurn.column,
                    OmokTurnContract.COLUMN_STONE_COLOR to omokTurn.stoneColor,
                ),
            )
        return omokTurn.copy(id = id)
    }

    fun findAll(): List<OmokTurn> {
        val db = sqLiteOpenHelper.readableDatabase
        return db.querySelectAll(
            OmokTurnContract.TABLE_NAME,
            OmokTurnContract.ALL_COLUMNS,
        ).use { cursor ->
            mutableListOf<OmokTurn>().apply {
                while (cursor.moveToNext()) {
                    val id = cursor.getLong(cursor.getColumnIndexOrThrow(OmokTurnContract.COLUMN_ID))
                    val row = cursor.getInt(cursor.getColumnIndexOrThrow(OmokTurnContract.COLUMN_POSITION_ROW))
                    val column = cursor.getInt(cursor.getColumnIndexOrThrow(OmokTurnContract.COLUMN_POSITION_COLUMN))
                    val stoneColor = cursor.getString(cursor.getColumnIndexOrThrow(OmokTurnContract.COLUMN_STONE_COLOR))

                    add(OmokTurn(row, column, stoneColor, id))
                }
            }
        }
    }

    fun findLatestStoneColor(): String? {
        val db = sqLiteOpenHelper.readableDatabase
        db.query(
            OmokTurnContract.TABLE_NAME,
            arrayOf(OmokTurnContract.COLUMN_STONE_COLOR),
            null,
            null,
            null,
            null,
            "${OmokTurnContract.COLUMN_ID} DESC",
            "1",
        ).use { cursor ->
            if (cursor.moveToFirst()) {
                return cursor.getString(cursor.getColumnIndexOrThrow(OmokTurnContract.COLUMN_STONE_COLOR))
            }
        }
        return null
    }

    fun drop() {
        val db = sqLiteOpenHelper.writableDatabase
        db.execSQL(OmokTurnContract.DROP_OMOK_TURN_TABLE)
    }
}

private fun SQLiteDatabase.querySelectAll(
    tableName: String,
    columns: Array<String>,
): Cursor =
    query(
        tableName,
        columns,
        null,
        null,
        null,
        null,
        null,
    )
