package woowacourse.omok.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import omok.OmokBoard
import omok.OmokPoint
import omok.state.BlackStoneState
import omok.state.StoneState
import omok.state.WhiteStoneState

class OmokDBHelper(context: Context) : SQLiteOpenHelper(context, "omok.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val sql = "CREATE TABLE if not exists ${OmokContract.TABLE_NAME}(" +
            "id integer primary key autoincrement," +
            "${OmokContract.TABLE_COLUMN_X} integer," +
            "${OmokContract.TABLE_COLUMN_Y} integer," +
            "${OmokContract.TABLE_COLUMN_STONE_COLOR} integer);"
        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val sql = "DROP TABLE if exists ${OmokContract.TABLE_NAME};"
        db?.execSQL(sql)
        onCreate(db)
    }

    fun insertData(point: OmokPoint, color: Int) {
        val db = this.writableDatabase
        val query =
            "INSERT INTO ${OmokContract.TABLE_NAME}(${OmokContract.TABLE_COLUMN_X}, ${OmokContract.TABLE_COLUMN_Y}, ${OmokContract.TABLE_COLUMN_STONE_COLOR}) values(${point.x}, ${point.y}, $color);"
        db.execSQL(query)
        db.close()
    }

    fun deleteAll() {
        val db = this.writableDatabase
        db.delete(
            OmokContract.TABLE_NAME,
            null,
            null
        )
        db.close()
    }

    fun selectAll(): OmokBoard? {
        val board = mutableMapOf<OmokPoint, StoneState>()
        val db = this.readableDatabase
        val cursor = db.query(
            OmokContract.TABLE_NAME,
            arrayOf(
                OmokContract.TABLE_COLUMN_X,
                OmokContract.TABLE_COLUMN_Y,
                OmokContract.TABLE_COLUMN_STONE_COLOR
            ),
            null,
            null,
            null,
            null,
            null
        )
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
        db.close()
        return if (board.isEmpty()) null else OmokBoard(board)
    }
}
