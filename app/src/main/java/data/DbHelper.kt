package data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import data.StateContract.BLACK_STONES_TABLE
import data.StateContract.COLUMN_ID
import data.StateContract.COLUMN_STATE
import data.StateContract.COLUMN_X
import data.StateContract.COLUMN_Y
import data.StateContract.DATABASE_NAME
import data.StateContract.DATABASE_VERSION
import data.StateContract.SQL_CREATE_BLACK_STONES
import data.StateContract.SQL_CREATE_STATE
import data.StateContract.SQL_CREATE_WHITE_STONES
import data.StateContract.SQL_DELETE_BLACK_STONES
import data.StateContract.SQL_DELETE_STATE
import data.StateContract.SQL_DELETE_WHITE_STONES
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

class DbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_STATE)
        db.execSQL(SQL_CREATE_BLACK_STONES)
        db.execSQL(SQL_CREATE_WHITE_STONES)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int,
    ) {
        db.execSQL(SQL_DELETE_STATE)
        db.execSQL(SQL_DELETE_BLACK_STONES)
        db.execSQL(SQL_DELETE_WHITE_STONES)
        onCreate(db)
    }

    fun saveGameState(state: State) {
        val db = writableDatabase

        val contentValues =
            ContentValues().apply {
                put(COLUMN_ID, 1)
                put(COLUMN_STATE, state.javaClass.simpleName)
            }
        db.insertWithOnConflict(TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE)

        db.delete(BLACK_STONES_TABLE, null, null)
        db.delete(WHITE_STONES_TABLE, null, null)

        if (state is Playing) {
            state.blackStones.points.forEach { point ->
                val blackStoneValues =
                    ContentValues().apply {
                        put(COLUMN_X, point.x)
                        put(COLUMN_Y, point.y)
                    }
                db.insert(BLACK_STONES_TABLE, null, blackStoneValues)
            }

            state.whiteStones.points.forEach { point ->
                val whiteStoneValues =
                    ContentValues().apply {
                        put(COLUMN_X, point.x)
                        put(COLUMN_Y, point.y)
                    }
                db.insert(WHITE_STONES_TABLE, null, whiteStoneValues)
            }
        }
        db.close()
    }

    fun loadGameState(): State? {
        val db = readableDatabase
        var state: State? = null

        val cursor = db.rawQuery("SELECT $COLUMN_STATE FROM $TABLE_NAME WHERE $COLUMN_ID = 1", null)

        val savedBlackStones = BlackStones(loadStones(BLACK_STONES_TABLE))
        val savedWhiteStones = WhiteStones(loadStones(WHITE_STONES_TABLE))

        if (cursor.moveToFirst()) {
            val stateType = cursor.getString(0)

            state =
                when (stateType) {
                    "Ready" -> Ready()
                    "BlackTurn" -> BlackTurn(savedBlackStones, savedWhiteStones)
                    "WhiteTurn" -> WhiteTurn(savedBlackStones, savedWhiteStones)
                    else -> null
                }
        }
        cursor.close()
        return state
    }

    private fun loadStones(tableName: String): Set<Point> {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT $COLUMN_X, $COLUMN_Y FROM $tableName", null)
        val stones = mutableSetOf<Point>()

        while (cursor.moveToNext()) {
            val x = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_X))
            val y = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_Y))
            stones.add(Point(x, y))
        }
        cursor.close()
        return stones
    }

    fun clearGameState() {
        val db = writableDatabase
        db.execSQL("DELETE FROM $TABLE_NAME")
        db.execSQL("DELETE FROM $BLACK_STONES_TABLE")
        db.execSQL("DELETE FROM $WHITE_STONES_TABLE")
        db.close()
    }
}
