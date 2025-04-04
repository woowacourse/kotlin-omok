package woowacourse.omok.database

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import woowacourse.omok.domain.Position
import woowacourse.omok.domain.Stone
import woowacourse.omok.domain.StoneType

class DatabaseStoneDAO(private val dbHelper: DbHelper) : StoneDAO {
    override fun insertStone(
        row: Int,
        column: Int,
        color: String,
    ) {
        dbHelper.writableDatabase.use { db ->
            if (existsStoneAt(db, row, column, color)) {
                return
            }

            val values =
                ContentValues().apply {
                    put(BoardContract.COLUMN_NAME_COLOR, color)
                    put(BoardContract.COLUMN_NAME_POSITION_ROW, row)
                    put(BoardContract.COLUMN_NAME_POSITION_COLUMN, column)
                }

            db.insert(BoardContract.TABLE_NAME_BOARD, null, values)
        }
    }

    private fun existsStoneAt(db: SQLiteDatabase, row: Int, column: Int, color: String): Boolean {
        db.rawQuery(
                "SELECT * FROM board WHERE position_row = ? AND position_column = ? AND color = ?",
                arrayOf(row.toString(), column.toString(), color),
            ).use { cursor ->
                return cursor.count > 0
        }
    }

    override fun queryStones(): List<Stone> {
        return dbHelper.readableDatabase.rawQuery("SELECT * FROM board", null).use {
            buildList {
                while (it.moveToNext()) {
                    val color = it.getString(it.getColumnIndexOrThrow(BoardContract.COLUMN_NAME_COLOR))
                    val row = it.getInt(it.getColumnIndexOrThrow(BoardContract.COLUMN_NAME_POSITION_ROW))
                    val column = it.getInt(it.getColumnIndexOrThrow(BoardContract.COLUMN_NAME_POSITION_COLUMN))
                    val stoneColor = if (color == "black") StoneType.BLACK else StoneType.WHITE
                    add(Stone(Position(row, column), stoneColor))
                }
            }
        }
    }

    override fun clear() {
        dbHelper.writableDatabase.use { db ->
            db.execSQL(BoardContract.SQL_DELETE_BOARD_ENTRIES)
            db.execSQL(BoardContract.SQL_CREATE_BOARD_ENTERIES)
        }
    }
}
