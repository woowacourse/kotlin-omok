package woowacourse.omok.data

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.omok.data.StateContract.BLACK_STONES_TABLE
import woowacourse.omok.data.StateContract.COLUMN_STATE
import woowacourse.omok.data.StateContract.COLUMN_X
import woowacourse.omok.data.StateContract.COLUMN_Y
import woowacourse.omok.data.StateContract.TABLE_NAME
import woowacourse.omok.data.StateContract.WHITE_STONES_TABLE
import woowacourse.omok.domain.model.Point
import woowacourse.omok.domain.model.state.State
import woowacourse.omok.domain.model.stone.BlackStones
import woowacourse.omok.domain.model.stone.StoneColor
import woowacourse.omok.domain.model.stone.WhiteStones

class OmokDao(private val dbHelper: SQLiteOpenHelper) {
    fun saveGameState(state: State) {
        dbHelper.writableDatabase.use { db ->
            val contentValues =
                ContentValues().apply {
                    put(COLUMN_STATE, state::class.java.simpleName)
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
            db.rawQuery("SELECT $COLUMN_STATE FROM $TABLE_NAME LIMIT 1", null).use { cursor ->
                if (cursor.moveToFirst()) {
                    val stateType = cursor.getString(0)
                    val savedBlackStones = BlackStones(loadStones(db, BLACK_STONES_TABLE))
                    val savedWhiteStones = WhiteStones(loadStones(db, WHITE_STONES_TABLE))
                    return when (stateType) {
                        "Playing" -> State.Playing(savedBlackStones, savedWhiteStones, StoneColor.BLACK)
                        "Finished" -> State.Finished(null)
                        else -> null
                    }
                }
            }
        }
        return null
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

    companion object {
        private const val TABLE_NAME = "game_state"
        private const val BLACK_STONES_TABLE = "black_stones"
        private const val WHITE_STONES_TABLE = "white_stones"
        private const val COLUMN_STATE = "state"
        private const val COLUMN_X = "x"
        private const val COLUMN_Y = "y"
    }
}
