package woowacourse.omok

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import domain.Color
import domain.Coordinate
import domain.Stone

class OmokGameDbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_STONE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    fun readOmokBoardData() : List<Stone> {
        val db = this.readableDatabase

        val cursor: Cursor = db.query(
            OmokGameContract.Stone.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )

        val stones = mutableListOf<Stone>()
        with(cursor) {
            while (moveToNext()) {
                val color =
                    if (getString(getColumnIndexOrThrow(OmokGameContract.Stone.COLOR)) == Color.BLACK.toString()) Color.BLACK else Color.WHITE
                val x = getInt(getColumnIndexOrThrow(OmokGameContract.Stone.X))
                val y = getInt(getColumnIndexOrThrow(OmokGameContract.Stone.Y))
                stones.add(Stone(color, Coordinate.from(x, y)))
            }
        }
        return stones
    }

    fun writeOmokStone(stone : Stone) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(OmokGameContract.Stone.X, stone.coordinate.x)
            put(OmokGameContract.Stone.Y, stone.coordinate.y)
            put(OmokGameContract.Stone.COLOR, stone.color.toString())
        }
        db?.insert(OmokGameContract.Stone.TABLE_NAME, null, values)
    }

    fun clearBoard() {
        val db = this.writableDatabase
        db.delete(OmokGameContract.Stone.TABLE_NAME, null, null)
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "omokGame.db"
        private const val SQL_CREATE_STONE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS ${OmokGameContract.Stone.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${OmokGameContract.Stone.COLOR} TEXT," +
                    "${OmokGameContract.Stone.X} INTEGER," +
                    "${OmokGameContract.Stone.Y} INTEGER)"
        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${OmokGameContract.Stone.TABLE_NAME}"
    }
}