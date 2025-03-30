package woowacourse.omok.data

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.omok.data.StateContract.BLACK_STONES_TABLE
import woowacourse.omok.data.StateContract.COLUMN_ID
import woowacourse.omok.data.StateContract.COLUMN_STATE
import woowacourse.omok.data.StateContract.COLUMN_X
import woowacourse.omok.data.StateContract.COLUMN_Y
import woowacourse.omok.data.StateContract.TABLE_NAME
import woowacourse.omok.data.StateContract.WHITE_STONES_TABLE
import woowacourse.omok.domain.model.Point
import woowacourse.omok.domain.model.state.BlackTurn
import woowacourse.omok.domain.model.state.Ready
import woowacourse.omok.domain.model.state.State
import woowacourse.omok.domain.model.state.WhiteTurn
import woowacourse.omok.domain.model.stone.BlackStones
import woowacourse.omok.domain.model.stone.WhiteStones

class OmokDao(private val dbHelper: SQLiteOpenHelper) {
    fun saveGameState(state: State) {
        dbHelper.writableDatabase.use { db ->
            val contentValues =
                ContentValues().apply {
                    put(COLUMN_STATE, state.javaClass.simpleName)
                }
            db.insertWithOnConflict(TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE)

            db.delete(BLACK_STONES_TABLE, null, null)
            db.delete(WHITE_STONES_TABLE, null, null)
        }
    }

    fun saveStone(
        tableName: String,
        point: Point,
    ) {
        dbHelper.writableDatabase.use { db ->
            val contentValues =
                ContentValues().apply {
                    put(COLUMN_X, point.x)
                    put(COLUMN_Y, point.y)
                }
            db.insert(tableName, null, contentValues)
        }
    }

    fun loadGameState(): State? {
        dbHelper.readableDatabase.use { db ->
            val cursor =
                db.rawQuery("SELECT $COLUMN_STATE FROM $TABLE_NAME WHERE $COLUMN_ID = 1", null)

            val savedBlackStones = BlackStones(loadStones(db, BLACK_STONES_TABLE))
            val savedWhiteStones = WhiteStones(loadStones(db, WHITE_STONES_TABLE))

            return if (cursor.moveToFirst()) {
                val stateType = cursor.getString(0)
                cursor.close()
                when (stateType) {
                    "Ready" -> Ready()
                    "BlackTurn" -> BlackTurn(savedBlackStones, savedWhiteStones)
                    "WhiteTurn" -> WhiteTurn(savedBlackStones, savedWhiteStones)
                    else -> null
                }
            } else {
                cursor.close()
                null
            }
        }
    }

    fun clearGameState() {
        dbHelper.writableDatabase.use { db ->
            db.execSQL("DELETE FROM $TABLE_NAME")
            db.execSQL("DELETE FROM $BLACK_STONES_TABLE")
            db.execSQL("DELETE FROM $WHITE_STONES_TABLE")
        }
    }

    private fun loadStones(
        db: SQLiteDatabase,
        tableName: String,
    ): Set<Point> {
        val stones = mutableSetOf<Point>()
        db.rawQuery("SELECT $COLUMN_X, $COLUMN_Y FROM $tableName", null).use { cursor ->
            while (cursor.moveToNext()) {
                val x = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_X))
                val y = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_Y))
                stones.add(Point(x, y))
            }
        }
        return stones
    }
}
