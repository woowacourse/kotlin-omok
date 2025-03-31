package woowacourse.omok.data

import android.content.ContentValues
import woowacourse.omok.domain.Position
import woowacourse.omok.domain.Stone
import woowacourse.omok.domain.StoneType

class OmokDao(private val dbHelper: OmokDbHelper) {
    fun createTable() {
        dbHelper.writableDatabase.execSQL(OmokContract.SQL_CREATE_ENTRIES)
    }

    fun insertStone(
        position: Position,
        stoneType: StoneType,
    ): Long {
        val db = dbHelper.writableDatabase
        val values =
            ContentValues().apply {
                put(OmokContract.COLUMN_NAME_X, position.x)
                put(OmokContract.COLUMN_NAME_Y, position.y)
                put(OmokContract.COLUMN_NAME_COLOR, stoneType.name)
            }
        return db.insert(OmokContract.TABLE_NAME, null, values)
    }

    fun getStone(position: Position): StoneType? {
        val db = dbHelper.readableDatabase
        val cursor =
            db.query(
                OmokContract.TABLE_NAME,
                arrayOf(OmokContract.COLUMN_NAME_COLOR),
                "${OmokContract.COLUMN_NAME_X} = ? AND ${OmokContract.COLUMN_NAME_Y} = ?",
                arrayOf(position.x.toString(), position.y.toString()),
                null,
                null,
                null,
            )

        cursor.use {
            if (it.moveToFirst()) {
                val color = it.getString(it.getColumnIndexOrThrow(OmokContract.COLUMN_NAME_COLOR))
                return StoneType.valueOf(color)
            }
        }
        return StoneType.EMPTY
    }

    fun getAllStones(): List<Stone> {
        val db = dbHelper.readableDatabase
        val stones = mutableListOf<Stone>()

        val cursor =
            db.query(
                OmokContract.TABLE_NAME,
                arrayOf(
                    OmokContract.COLUMN_NAME_X,
                    OmokContract.COLUMN_NAME_Y,
                    OmokContract.COLUMN_NAME_COLOR,
                ),
                null,
                null,
                null,
                null,
                null,
            )

        cursor.use {
            while (it.moveToNext()) {
                val x = it.getInt(it.getColumnIndexOrThrow(OmokContract.COLUMN_NAME_X))
                val y = it.getInt(it.getColumnIndexOrThrow(OmokContract.COLUMN_NAME_Y))
                val color = it.getString(it.getColumnIndexOrThrow(OmokContract.COLUMN_NAME_COLOR))
                stones.add(Stone(Position(x, y), StoneType.valueOf(color)))
            }
        }

        return stones
    }

    fun clearBoard() {
        val db = dbHelper.writableDatabase
        db.delete(OmokContract.TABLE_NAME, null, null)
    }
}
