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
import woowacourse.omok.MainActivity.Companion.changeIndexToPosition
import woowacourse.omok.StoneModel.toStone

class BoardDBHelper(context: Context?) :
    SQLiteOpenHelper(context, BoardContract.DATABASE_NAME, null, 1) {
    private val db = this.writableDatabase
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE ${BoardContract.TABLE_NAME}(" +
                " ${BoardContract.TABLE_COLUMN_POSITION_INDEX} int, " +
                " ${BoardContract.TABLE_COLUMN_STONE} varchar(10)" +
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
            val stoneColor = cursor.getString(cursor.getColumnIndexOrThrow(BoardContract.TABLE_COLUMN_STONE))
            cells[changeIndexToPosition(index)] = stoneColor.toStone()
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
            put(BoardContract.TABLE_COLUMN_STONE, "$currentStone")
        }
        db.insert(BoardContract.TABLE_NAME, null, values)
    }

    fun deleteData() {
        db.execSQL("DELETE FROM ${BoardContract.TABLE_NAME}")
        db.close()
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${BoardContract.TABLE_NAME}")
        onCreate(db)
    }
}
