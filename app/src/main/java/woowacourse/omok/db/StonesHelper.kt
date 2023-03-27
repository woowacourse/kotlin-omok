package woowacourse.omok.db

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import domain.stone.Color
import domain.stone.Position
import domain.stone.Stone
import woowacourse.omok.db.vo.StoneVO

class StonesHelper(
    context: Context,
    private val roomId: Int
) : SQLiteOpenHelper(context, db_name, null, db_version) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE ${StoneConstract.TABLE_NAME}(" +
                "  ${StoneConstract.TABLE_COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "  ${StoneConstract.TABLE_COLUMN_ROOM_NUMBER} int," +
                "  ${StoneConstract.TABLE_COLUMN_X} int," +
                "  ${StoneConstract.TABLE_COLUMN_Y} int," +
                "  ${StoneConstract.TABLE_COLUMN_COLOR} int" +
                ");"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${StoneConstract.TABLE_NAME}")
        onCreate(db)
    }

    fun insertStone(stone: Stone) {
        this.writableDatabase.execSQL(
            "INSERT INTO ${StoneConstract.TABLE_NAME} (${StoneConstract.TABLE_COLUMN_ROOM_NUMBER}, ${StoneConstract.TABLE_COLUMN_COLOR}, ${StoneConstract.TABLE_COLUMN_X}, ${StoneConstract.TABLE_COLUMN_Y})" +
                "VALUES ($roomId, ${stone.color.ordinal}, ${stone.position.column.ordinal}, ${stone.position.row.ordinal});"
        )
    }

    fun getAlreadyPutStones(): List<Stone> {
        val cursor = getAllStonesCursor()
        val stoneVOs = mutableListOf<StoneVO>().apply {
            while (cursor.moveToNext()) {
                add(convertCursorToStoneVO(cursor))
            }
        }
        cursor.close()
        return stoneVOs.map { Stone(it.position, it.stoneColor) }
    }

    private fun getAllStonesCursor(): Cursor {
        return this.writableDatabase.query(
            StoneConstract.TABLE_NAME,
            StoneConstract.allColumn(),
            "${StoneConstract.TABLE_COLUMN_ROOM_NUMBER} = ?",
            arrayOf(roomId.toString()),
            null,
            null,
            "${StoneConstract.TABLE_COLUMN_ID} ASC"
        )
    }

    private fun convertCursorToStoneVO(cursor: Cursor): StoneVO = StoneVO(
        cursor.getInt(cursor.getColumnIndexOrThrow(StoneConstract.TABLE_COLUMN_ID)),
        Color.values()[cursor.getInt(cursor.getColumnIndexOrThrow(StoneConstract.TABLE_COLUMN_COLOR))],
        Position(
            cursor.getInt(cursor.getColumnIndexOrThrow(StoneConstract.TABLE_COLUMN_X)) + 1,
            cursor.getInt(cursor.getColumnIndexOrThrow(StoneConstract.TABLE_COLUMN_Y)) + 1
        ),
        cursor.getInt(cursor.getColumnIndexOrThrow(StoneConstract.TABLE_COLUMN_ROOM_NUMBER))
    )

    fun deleteAll() {
        this.writableDatabase.execSQL(
            "DELETE FROM ${StoneConstract.TABLE_NAME} " +
                "WHERE ${StoneConstract.TABLE_COLUMN_ROOM_NUMBER} = '$roomId';"
        )
    }

    companion object {
        private const val db_name = "omok_db"
        private const val db_version = 6
    }
}
