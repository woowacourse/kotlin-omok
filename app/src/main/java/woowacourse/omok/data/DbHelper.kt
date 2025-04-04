package woowacourse.omok.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.omok.domain.Point
import woowacourse.omok.domain.stone.OmokStones
import woowacourse.omok.domain.stone.Stone
import woowacourse.omok.domain.stone.StoneColor

class DbHelper(
    context: Context,
    name: String = DATABASE_NAME,
) : SQLiteOpenHelper(context, name, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(OmokContract.SQL_CREATE_STONES)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int,
    ) {
        db.execSQL(OmokContract.SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int,
    ) {
        onUpgrade(db, oldVersion, newVersion)
    }

    fun insertStone(stone: Stone) {
        writableDatabase.use { db ->
            val values =
                ContentValues().apply {
                    put(OmokContract.COLUMN_NAME_COLOR, stone.color.name)
                    put(OmokContract.COLUMN_NAME_ROW, stone.point.row)
                    put(OmokContract.COLUMN_NAME_COLUMN, stone.point.col)
                }
            db.insert(OmokContract.TABLE_NAME, null, values)
        }
    }

    fun queryStones(): OmokStones {
        val result = mutableSetOf<Stone>()
        readableDatabase.use { db ->
            db.rawQuery(QUERY_GET_STONES, null).use { cursor ->
                with(cursor) {
                    while (moveToNext()) {
                        val color =
                            StoneColor.valueOf(getString(getColumnIndexOrThrow(OmokContract.COLUMN_NAME_COLOR)))
                        val row = getInt(getColumnIndexOrThrow(OmokContract.COLUMN_NAME_ROW))
                        val col = getInt(getColumnIndexOrThrow(OmokContract.COLUMN_NAME_COLUMN))
                        result.add(Stone(color, Point(row, col)))
                    }
                }
            }
        }
        return OmokStones(result)
    }

    fun queryLastStone(): Stone? {
        readableDatabase.use { db ->
            val query = QUERY_GET_LAST_STONE
            db.rawQuery(query, null).use { cursor ->
                with(cursor) {
                    if (moveToFirst()) {
                        val color =
                            StoneColor.valueOf(getString(cursor.getColumnIndexOrThrow(OmokContract.COLUMN_NAME_COLOR)))
                        val row =
                            getInt(cursor.getColumnIndexOrThrow(OmokContract.COLUMN_NAME_ROW))
                        val col =
                            getInt(cursor.getColumnIndexOrThrow(OmokContract.COLUMN_NAME_COLUMN))
                        Stone(color, Point(row, col))
                    }
                }
            }
        }
        return null
    }

    fun deleteStones() {
        writableDatabase.use { db ->
            db.delete(OmokContract.TABLE_NAME, null, null)
        }
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Omok.db"
        private const val QUERY_GET_STONES = "SELECT * FROM ${OmokContract.TABLE_NAME}"
        private const val QUERY_GET_LAST_STONE =
            "SELECT * FROM ${OmokContract.TABLE_NAME} ORDER BY ${OmokContract.COLUMN_NAME_ID} DESC LIMIT 1"
    }
}
