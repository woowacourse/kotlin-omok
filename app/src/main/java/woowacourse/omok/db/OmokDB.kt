package woowacourse.omok.db

import android.content.Context
import android.database.Cursor
import omok.OmokBoard
import omok.OmokPoint
import omok.gameState.BlackTurn
import omok.gameState.GameState
import omok.gameState.WhiteTurn
import omok.state.BlackStoneState
import omok.state.StoneState
import omok.state.WhiteStoneState

class OmokDB(context: Context) {
    private val db = OmokDBHelper(context).writableDatabase

    fun recordStoneInfo(point: OmokPoint, color: Int) {
        val query =
            "INSERT INTO ${OmokContract.TABLE_NAME}(${OmokContract.TABLE_COLUMN_X}, ${OmokContract.TABLE_COLUMN_Y}, ${OmokContract.TABLE_COLUMN_STONE_COLOR}) values(${point.x}, ${point.y}, $color);"
        db.execSQL(query)
    }

    fun getBoard(): GameState {
        val cursor = db.rawQuery("SELECT * FROM ${OmokContract.TABLE_NAME}", null)
        val tempBoard = getStonesInfo(cursor)
        val board = OmokBoard().initBoard(tempBoard)
        cursor.close()
        return when (determineTurn(tempBoard.size)) {
            BlackStoneState -> BlackTurn(board)
            else -> WhiteTurn(board)
        }
    }

    private fun determineTurn(size: Int): StoneState = if (size % 2 == 0) BlackStoneState else WhiteStoneState

    private fun getStonesInfo(cursor: Cursor): Map<OmokPoint, StoneState> {
        val board = mutableMapOf<OmokPoint, StoneState>()
        while (cursor.moveToNext()) {
            board[
                OmokPoint(
                    cursor.getInt(cursor.getColumnIndexOrThrow(OmokContract.TABLE_COLUMN_X)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(OmokContract.TABLE_COLUMN_Y))
                )
            ] =
                if (cursor.getInt(cursor.getColumnIndexOrThrow(OmokContract.TABLE_COLUMN_STONE_COLOR)) == 0) BlackStoneState else WhiteStoneState
        }
        cursor.close()
        return board
    }

    fun clearStoneInfo() {
        db.delete(
            OmokContract.TABLE_NAME,
            null,
            null
        )
    }
}
