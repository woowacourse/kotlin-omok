package woowacourse.omok.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class GameRecordDao(private val omokSQLiteHelper: SQLiteOpenHelper) {
    fun createTable() {
        omokSQLiteHelper.writableDatabase.use {
            it.execSQL(OmokContract.GameRecordEntry.SQL_CREATE_TABLE)
        }
    }

    @SuppressLint("Range")
    fun selectAll(): List<GameTurnEntity> {
        val db = omokSQLiteHelper.readableDatabase
        val turns = mutableListOf<GameTurnEntity>()
        db.rawQuery(OmokContract.GameRecordEntry.SQL_SELECT_ALL, null)
            .use { cursor ->
                while (cursor.moveToNext()) {
                    val id = cursor.getLong(cursor.getColumnIndex(BaseColumns._ID))
                    val x =
                        cursor.getInt(cursor.getColumnIndex(OmokContract.GameRecordEntry.COLUMN_X))
                    val y =
                        cursor.getInt(cursor.getColumnIndex(OmokContract.GameRecordEntry.COLUMN_Y))
                    val color =
                        cursor.getString(cursor.getColumnIndex(OmokContract.GameRecordEntry.COLUMN_COLOR))
                    turns.add(GameTurnEntity(id, x, y, color))
                }
            }
        db.close()
        return turns
    }

    fun saveGame(turns: List<GameTurnEntity>): List<Long> {
        return turns.map(::saveTurn)
    }

    fun saveTurn(turn: GameTurnEntity): Long {
        val (_, x, y, color) = turn
        val db = omokSQLiteHelper.writableDatabase
        return contentValues {
            put(OmokContract.GameRecordEntry.COLUMN_X, x)
            put(OmokContract.GameRecordEntry.COLUMN_Y, y)
            put(OmokContract.GameRecordEntry.COLUMN_COLOR, color)
        }.let {
            db.insertOrThrow(OmokContract.GameRecordEntry.TABLE_NAME, null, it)
        }.also {
            omokSQLiteHelper.close()
        }
    }

    fun resetTable(): Int {
        omokSQLiteHelper.writableDatabase.use {
            return it.delete(OmokContract.GameRecordEntry.TABLE_NAME, null, null)
        }
    }

    fun dropTable() {
        omokSQLiteHelper.writableDatabase.use {
            it.execSQL(OmokContract.GameRecordEntry.SQL_DELETE_TABLE)
        }
    }

    private fun contentValues(action: ContentValues.() -> Unit): ContentValues {
        return ContentValues().apply(action)
    }
}
