package woowacourse.omok

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import omok.domain.OmokBoard
import omok.domain.OmokPoint
import omok.domain.gameState.BlackTurn
import omok.domain.gameState.BlackWin
import omok.domain.gameState.GameState
import omok.domain.gameState.WhiteTurn
import omok.domain.gameState.WhiteWin
import omok.domain.state.BlackStoneState
import omok.domain.state.EmptyStoneState
import omok.domain.state.StoneState
import omok.domain.state.WhiteStoneState
import woowacourse.omok.OmokBoardConstract.TABLE_COLUMN_OMOK_COL
import woowacourse.omok.OmokBoardConstract.TABLE_COLUMN_OMOK_GAME_ID
import woowacourse.omok.OmokBoardConstract.TABLE_COLUMN_OMOK_NEXT_TURN
import woowacourse.omok.OmokBoardConstract.TABLE_COLUMN_OMOK_ROW
import woowacourse.omok.OmokBoardConstract.TABLE_COLUMN_OMOK_STONE
import woowacourse.omok.OmokBoardConstract.TABLE_NAME_OMOK_BOARD

class OmokBoardDbHelper(context: Context?) : SQLiteOpenHelper(context, "ark.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE $TABLE_NAME_OMOK_BOARD (" +
                "  $TABLE_COLUMN_OMOK_GAME_ID int," +
                "  $TABLE_COLUMN_OMOK_ROW int," +
                "  $TABLE_COLUMN_OMOK_COL int," +
                "  $TABLE_COLUMN_OMOK_NEXT_TURN int," +
                "  $TABLE_COLUMN_OMOK_STONE int" +
                ");",
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_OMOK_BOARD")
        onCreate(db)
    }

    fun insert(gameState: GameState, omokPoint: OmokPoint, gameId: Int) {
        val wDb = this.writableDatabase

        val values = ContentValues()
        values.put(TABLE_COLUMN_OMOK_GAME_ID, gameId)
        values.put(TABLE_COLUMN_OMOK_COL, omokPoint.x.value)
        values.put(TABLE_COLUMN_OMOK_ROW, omokPoint.y.value)
        values.put(
            TABLE_COLUMN_OMOK_NEXT_TURN,
            when (gameState) {
                is BlackTurn -> 1
                is BlackWin -> 2
                is WhiteTurn -> 3
                is WhiteWin -> 4
            },
        )
        values.put(
            TABLE_COLUMN_OMOK_STONE,
            when (gameState.omokBoard[omokPoint]) {
                BlackStoneState -> 1
                WhiteStoneState -> 2
                else -> 0
            },
        )

        wDb.insert(TABLE_NAME_OMOK_BOARD, null, values)
    }

    fun getGameState(gameId: Int): GameState {
        val rDb = this.readableDatabase
        val cursor = rDb.query(
            TABLE_NAME_OMOK_BOARD,
            arrayOf(
                TABLE_COLUMN_OMOK_GAME_ID,
                TABLE_COLUMN_OMOK_COL,
                TABLE_COLUMN_OMOK_ROW,
                TABLE_COLUMN_OMOK_NEXT_TURN,
                TABLE_COLUMN_OMOK_STONE,
            ),
            "$TABLE_COLUMN_OMOK_GAME_ID = ?",
            arrayOf(gameId.toString()),
            null,
            null,
            null,
        )

        val omokMap = mutableMapOf<OmokPoint, StoneState>()

        with(cursor) {
            while (moveToNext()) {
                val col = getInt(getColumnIndexOrThrow(TABLE_COLUMN_OMOK_COL))
                val row = getInt(getColumnIndexOrThrow(TABLE_COLUMN_OMOK_ROW))
                val nextTurn =
                    getInt(getColumnIndexOrThrow(TABLE_COLUMN_OMOK_NEXT_TURN))
                val stone =
                    getInt(getColumnIndexOrThrow(TABLE_COLUMN_OMOK_STONE))

                omokMap[OmokPoint(col, row)] = when (stone) {
                    1 -> BlackStoneState
                    2 -> WhiteStoneState
                    else -> EmptyStoneState
                }

                if (isLast) {
                    when (nextTurn) {
                        1 -> BlackTurn(OmokBoard(omokMap))
                        2 -> BlackWin(OmokBoard(omokMap))
                        3 -> WhiteTurn(OmokBoard(omokMap))
                        4 -> WhiteWin(OmokBoard(omokMap))
                        else -> null
                    }?.let {
                        return it
                    }
                }
            }
        }

        return BlackTurn(OmokBoard(omokMap))
    }

    fun delete(gameId: Int) {
        val wDb = this.writableDatabase
        wDb.delete(TABLE_NAME_OMOK_BOARD, "$TABLE_COLUMN_OMOK_GAME_ID = ?", arrayOf(gameId.toString()))
    }
}
