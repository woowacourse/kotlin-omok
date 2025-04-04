package woowacourse.omok.data

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.omok.data.StateContract.BLACK_STONES_TABLE
import woowacourse.omok.data.StateContract.COLUMN_ROOM_ID
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
    fun saveGameState(
        roomId: String,
        state: State,
    ) {
        dbHelper.writableDatabase.use { db ->
            val contentValues =
                ContentValues().apply {
                    put(COLUMN_ROOM_ID, roomId)
                    put(COLUMN_STATE, state::class.java.simpleName)
                }
            db.insertWithOnConflict(
                TABLE_NAME,
                null,
                contentValues,
                SQLiteDatabase.CONFLICT_REPLACE,
            )

            db.delete(BLACK_STONES_TABLE, "$COLUMN_ROOM_ID = ?", arrayOf(roomId))
            db.delete(WHITE_STONES_TABLE, "$COLUMN_ROOM_ID = ?", arrayOf(roomId))
        }
    }

    fun saveStone(
        roomId: String,
        tableName: String,
        point: Point,
    ) {
        dbHelper.writableDatabase.use { db ->
            val contentValues =
                ContentValues().apply {
                    put(COLUMN_ROOM_ID, roomId)
                    put(COLUMN_X, point.x)
                    put(COLUMN_Y, point.y)
                }
            db.insert(tableName, null, contentValues)
        }
    }

    fun loadGameState(roomId: String): State? {
        dbHelper.readableDatabase.use { db ->
            db.rawQuery(
                "SELECT $COLUMN_STATE FROM $TABLE_NAME WHERE $COLUMN_ROOM_ID = ? LIMIT 1",
                arrayOf(roomId.toString()),
            ).use { cursor ->
                if (cursor.moveToFirst()) {
                    val stateType = cursor.getString(0)
                    val savedBlackStones = BlackStones(loadStones(db, BLACK_STONES_TABLE, roomId))
                    val savedWhiteStones = WhiteStones(loadStones(db, WHITE_STONES_TABLE, roomId))

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

    fun clearGameState(roomId: String) {
        dbHelper.writableDatabase.use { db ->
            db.execSQL("DELETE FROM $TABLE_NAME WHERE $COLUMN_ROOM_ID = ?", arrayOf(roomId.toString()))
            db.execSQL("DELETE FROM $BLACK_STONES_TABLE WHERE $COLUMN_ROOM_ID = ?", arrayOf(roomId.toString()))
            db.execSQL("DELETE FROM $WHITE_STONES_TABLE WHERE $COLUMN_ROOM_ID = ?", arrayOf(roomId.toString()))
        }
    }

    private fun loadStones(
        db: SQLiteDatabase,
        tableName: String,
        roomId: String,
    ): Set<Point> {
        val stones = mutableSetOf<Point>()
        db.rawQuery(
            "SELECT ${COLUMN_X}, $COLUMN_Y FROM $tableName WHERE $COLUMN_ROOM_ID = ?",
            arrayOf(roomId.toString()),
        ).use { cursor ->
            while (cursor.moveToNext()) {
                val x = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_X))
                val y = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_Y))
                stones.add(Point(x, y))
            }
        }
        return stones
    }
}
