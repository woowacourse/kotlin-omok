package woowacourse.omok.data.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import domain.CoordinateState
import domain.CoordinateState.BLACK
import domain.CoordinateState.EMPTY
import domain.CoordinateState.WHITE
import domain.Position
import domain.domain.Stones
import domain.domain.state.BlackTurn
import domain.domain.state.State
import domain.domain.state.WhiteTurn
import woowacourse.omok.data.OmokContract.Board.CREATE_BOARD_TABLE
import woowacourse.omok.data.OmokContract.Board.DELETE_BOARD_TABLE
import woowacourse.omok.data.OmokContract.Board.TABLE_COLUMN_COLOR
import woowacourse.omok.data.OmokContract.Board.TABLE_COLUMN_ID
import woowacourse.omok.data.OmokContract.Board.TABLE_COLUMN_X
import woowacourse.omok.data.OmokContract.Board.TABLE_COLUMN_Y
import woowacourse.omok.data.OmokContract.Board.TABLE_NAME
import woowacourse.omok.data.OmokDbHelper

class BoardDao(private val context: Context) {
    private val omokDb by lazy { OmokDbHelper(context).readableDatabase }

    fun insertStone(roomId: Int, y: Int, x: Int, color: CoordinateState) {
        val data = ContentValues()
        data.put(TABLE_COLUMN_ID, roomId)
        data.put(TABLE_COLUMN_Y, y)
        data.put(TABLE_COLUMN_X, x)
        data.put(TABLE_COLUMN_COLOR, color.ordinal)
        omokDb.insert(TABLE_NAME, null, data)
    }

    fun readBoard(roomId: Int): State {
        val cursor = makeBoardCursor(roomId)
        val state = makeStones(cursor)
        cursor.close()
        return state
    }

    private fun makeStones(cursor: Cursor): State {
        val stones = Stones()
        var lastColor: CoordinateState = WHITE
        while (cursor.moveToNext()) {
            val y: Int = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_COLUMN_Y))
            val x: Int = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_COLUMN_X))
            val colorOrdinal = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_COLUMN_COLOR))
            val color: CoordinateState = CoordinateState.findByOrdinal(colorOrdinal)
            stones.addStone(Position(y, x), color)
            lastColor = color
        }
        return lastColor.toState(stones)
    }

    private fun CoordinateState.toState(stones: Stones): State {
        return when (this) {
            BLACK -> WhiteTurn(stones)
            WHITE -> BlackTurn(stones)
            EMPTY -> throw IllegalArgumentException("잘못된 인자입니다. EMPTY가 들어왔습니다.")
        }
    }

    private fun makeBoardCursor(roomId: Int): Cursor {
        return omokDb.query(
            TABLE_NAME,
            arrayOf(TABLE_COLUMN_ID, TABLE_COLUMN_Y, TABLE_COLUMN_X, TABLE_COLUMN_COLOR),
            "$TABLE_COLUMN_ID == ?",
            arrayOf("$roomId"),
            null,
            null,
            "$TABLE_COLUMN_ID ASC",
        )
    }

    fun closeDb() {
        omokDb.close()
    }

    fun clear() {
        omokDb.execSQL(DELETE_BOARD_TABLE)
        omokDb.execSQL(CREATE_BOARD_TABLE)
    }
}
