package woowacourse.omok

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import omok.domain.Turn
import omok.domain.board.Board
import omok.domain.board.Position
import omok.domain.player.Stone

class BoardDBHelper(context: Context?) :
    SQLiteOpenHelper(context, BoardContract.DATABASE_NAME, null, 1) {
    private val db = this.writableDatabase
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE ${BoardContract.TABLE_NAME}(" +
                " ${BoardContract.TABLE_COLUMN_POSITION_INDEX} int, " +
                " ${BoardContract.TABLE_COLUMN_STONE} int" +
                ");"
        )
    }

    fun getBoard(): Board {
        val cells: MutableMap<Position, Stone?> = Board.POSITIONS.associateWith { null }.toMutableMap()

        val cursor = db.rawQuery("SELECT * FROM ${BoardContract.TABLE_NAME}", null)
        if (cursor.moveToFirst()) makeCells(cursor, cells)
        cursor.close()
        return Board(cells)
    }

    private fun makeCells(
        cursor: Cursor,
        cells: MutableMap<Position, Stone?>
    ) {
        do {
            val index = cursor.getInt(cursor.getColumnIndexOrThrow(BoardContract.TABLE_COLUMN_POSITION_INDEX))
            val stone = cursor.getInt(cursor.getColumnIndexOrThrow(BoardContract.TABLE_COLUMN_STONE))
            cells[changeIndexToPosition(index)] = changeDataToStone(stone)
        } while (cursor.moveToNext())
    }

    fun getTurn(): Turn {
        val cursor = db.rawQuery("SELECT * FROM ${BoardContract.TABLE_NAME}", null)
        val count = cursor.count

        cursor.close()
        if (count != 0 && count % 2 == 1)
            return Turn(setOf(Stone.WHITE, Stone.BLACK))
        return Turn(setOf(Stone.BLACK, Stone.WHITE))
    }

    fun insertData(indexPosition: Int, currentStone: Stone) {
        val values = ContentValues().apply {
            put(BoardContract.TABLE_COLUMN_POSITION_INDEX, indexPosition)
            put(BoardContract.TABLE_COLUMN_STONE, "${changeStoneToData(currentStone)}")
        }
        db.insert(BoardContract.TABLE_NAME, null, values)
    }

    fun deleteData() {
        db.execSQL("DELETE FROM ${BoardContract.TABLE_NAME}")
        db.close()
    }

    private fun changeStoneToData(stone: Stone) = if (stone == Stone.BLACK) 0 else 1

    private fun changeDataToStone(stone: Int) = if (stone == 0) Stone.BLACK else Stone.WHITE

    private fun changeIndexToPosition(index: Int): Position {
        val row = 14 - (index / 15)
        val column = index % 15
        return Position(Pair(column, row))
    }
    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${BoardContract.TABLE_NAME}")
        onCreate(db)
    }
}
