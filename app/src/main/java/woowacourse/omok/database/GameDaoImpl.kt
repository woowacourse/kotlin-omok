package woowacourse.omok.database

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import woowacourse.omok.model.board.Stone
import woowacourse.omok.model.omokGame.Board.Companion.BOARD_SIZE

class GameDaoImpl(private val dbHelper: DatabaseHelper) : GameDao {

    override fun saveGame(board: Array<Array<Stone>>) {
        val db = dbHelper.writableDatabase
        db.beginTransaction()
        try {
            db.delete(GameBoardContract.GameBoardEntry.TABLE_NAME, null, null)

            for (i in board.indices) {
                for (j in board[i].indices) {
                    val stone = board[i][j]
                    if (stone != Stone.EMPTY) {
                        val stoneType = when (stone) {
                            Stone.BLACK -> 1
                            Stone.WHITE -> 2
                            else -> 0
                        }
                        val values = ContentValues().apply {
                            put(GameBoardContract.GameBoardEntry.COLUMN_ROW_INDEX, i)
                            put(GameBoardContract.GameBoardEntry.COLUMN_COLUMN_INDEX, j)
                            put(GameBoardContract.GameBoardEntry.COLUMN_STONE_TYPE, stoneType)
                        }
                        db.insert(GameBoardContract.GameBoardEntry.TABLE_NAME, null, values)
                    }
                }
            }
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }


    override fun loadGame(): Array<Array<Stone>> {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            GameBoardContract.GameBoardEntry.TABLE_NAME,
            arrayOf(
                GameBoardContract.GameBoardEntry.COLUMN_ROW_INDEX,
                GameBoardContract.GameBoardEntry.COLUMN_COLUMN_INDEX,
                GameBoardContract.GameBoardEntry.COLUMN_STONE_TYPE
            ),
            null, null, null, null, null
        )
        val board = Array(BOARD_SIZE) { Array(BOARD_SIZE) { Stone.EMPTY } }
        with(cursor) {
            while (moveToNext()) {
                val rowIndex = getIntSafe(GameBoardContract.GameBoardEntry.COLUMN_ROW_INDEX)
                val columnIndex = getIntSafe(GameBoardContract.GameBoardEntry.COLUMN_COLUMN_INDEX)
                val stoneType = getIntSafe(GameBoardContract.GameBoardEntry.COLUMN_STONE_TYPE)
                if (rowIndex != null && columnIndex != null && stoneType != null) {
                    val stone = when (stoneType) {
                        1 -> Stone.BLACK
                        2 -> Stone.WHITE
                        else -> Stone.EMPTY
                    }
                    board[rowIndex][columnIndex] = stone
                }
            }
            close()
        }
        return board
    }

    override fun resetGame() {
        val db = dbHelper.writableDatabase
        db.execSQL("DELETE FROM GameBoard")
    }

    override fun saveCurrentStone(currentStone: Int) {
        val db = resetCurrentStone()
        val values = ContentValues().apply {
            put(GameBoardContract.GameStatusEntry.COLUMN_CURRENT_STONE, currentStone)
        }
        db.insert(GameBoardContract.GameStatusEntry.TABLE_NAME, null, values)
    }


    private fun resetCurrentStone(): SQLiteDatabase {
        val db = dbHelper.writableDatabase
        db.execSQL("DELETE FROM GameStatus")
        return db
    }

    override fun loadCurrentStone(): Int {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            GameBoardContract.GameStatusEntry.TABLE_NAME,
            arrayOf(GameBoardContract.GameStatusEntry.COLUMN_CURRENT_STONE),
            null, null, null, null, null
        )
        var stoneType = -1
        val columnIndex =
            cursor.getColumnIndex(GameBoardContract.GameStatusEntry.COLUMN_CURRENT_STONE)
        if (columnIndex != -1 && cursor.moveToFirst()) {
            stoneType = cursor.getInt(columnIndex)
        }
        cursor.close()
        return stoneType
    }


    private fun Cursor.getIntSafe(columnName: String): Int? {
        val columnIndex = getColumnIndex(columnName)
        return if (columnIndex != -1) {
            getInt(columnIndex)
        } else {
            null
        }
    }

}
