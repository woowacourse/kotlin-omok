package data

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import data.StateContract.BLACK_STONES_TABLE
import data.StateContract.COLUMN_ID
import data.StateContract.COLUMN_STATE
import data.StateContract.COLUMN_X
import data.StateContract.COLUMN_Y
import data.StateContract.TABLE_NAME
import data.StateContract.WHITE_STONES_TABLE
import domain.domain.Point
import domain.domain.state.BlackTurn
import domain.domain.state.Playing
import domain.domain.state.Ready
import domain.domain.state.State
import domain.domain.state.WhiteTurn
import domain.domain.stone.BlackStones
import domain.domain.stone.WhiteStones

class OmokDao(private val dbHelper: DbHelper) {
    fun saveGameState(state: State) {
        dbHelper.writableDatabase.use { db ->
            val contentValues =
                ContentValues().apply {
                    put(COLUMN_ID, 1)
                    put(COLUMN_STATE, state.javaClass.simpleName)
                }
            db.insertWithOnConflict(
                TABLE_NAME,
                null,
                contentValues,
                SQLiteDatabase.CONFLICT_REPLACE,
            )

            db.delete(BLACK_STONES_TABLE, null, null)
            db.delete(WHITE_STONES_TABLE, null, null)

            if (state is Playing) {
                state.blackStones.points.forEach { point ->
                    saveStone(db, BLACK_STONES_TABLE, point)
                }
                state.whiteStones.points.forEach { point ->
                    saveStone(db, WHITE_STONES_TABLE, point)
                }
            }
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

    private fun saveStone(
        db: SQLiteDatabase,
        tableName: String,
        point: Point,
    ) {
        val contentValues =
            ContentValues().apply {
                put(COLUMN_X, point.x)
                put(COLUMN_Y, point.y)
            }
        db.insert(tableName, null, contentValues)
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
