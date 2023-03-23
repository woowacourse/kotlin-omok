package woowacourse.omok.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.provider.BaseColumns
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
import woowacourse.omok.data.OmokContract.Board.TABLE_COLUMN_COLOR
import woowacourse.omok.data.OmokContract.Board.TABLE_COLUMN_X
import woowacourse.omok.data.OmokContract.Board.TABLE_COLUMN_Y
import woowacourse.omok.data.OmokContract.Board.TABLE_NAME

class BoardTableModifier(private val context: Context) {
    private val omokDb by lazy { OmokDbHelper(context).readableDatabase }

    fun insertStone(y: Int, x: Int, color: CoordinateState) {
        val data = ContentValues()
        data.put(TABLE_COLUMN_Y, y)
        data.put(TABLE_COLUMN_X, x)
        data.put(TABLE_COLUMN_COLOR, color.ordinal)
        omokDb.insert(TABLE_NAME, null, data)
    }

    fun readBoard(): State {
        val cursor = makeBoardCursor()
        val stones = makeStones(cursor)
        cursor.close()

        return stones
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

    private fun makeBoardCursor(): Cursor {
        return omokDb.query(
            TABLE_NAME,
            arrayOf(TABLE_COLUMN_Y, TABLE_COLUMN_X, TABLE_COLUMN_COLOR),
            null,
            null,
            null,
            null,
            "${BaseColumns._ID} ASC",
        )
    }

    fun closeDb() {
        omokDb.close()
    }

    fun clear() {
        omokDb.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        omokDb.execSQL(CREATE_BOARD_TABLE)
    }
}
