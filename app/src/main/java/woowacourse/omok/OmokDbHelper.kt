package woowacourse.omok

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import domain.* // ktlint-disable no-wildcard-imports

class OmokDbHelper(context: Context?) :
    SQLiteOpenHelper(context, "${OmokContract.TABLE_NAME}", null, 1) {

    private val omokWritableDb: SQLiteDatabase = this.writableDatabase
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE ${OmokContract.TABLE_NAME} (" +
                "${OmokContract.TABLE_COLUMN_COLOR} varchar(2) not null," +
                "${OmokContract.TABLE_COLUMN_X} int not null," +
                "${OmokContract.TABLE_COLUMN_Y} int not null," +
                "UNIQUE (x,y)" +
                ");",
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${OmokContract.TABLE_NAME}")
        onCreate(db)
    }

    fun getOmokGame(rule: Rule): OmokGame {
        val stones = getAllStonesInDatabase()
        return OmokGame(Board(stones, rule))
    }

    fun updateOmokDatabase(stone: Stone) {
        val contentValues = getStoneContentValues(stone)
        omokWritableDb.insert(OmokContract.TABLE_NAME, null, contentValues)
    }

    fun deleteOmokDatabase() {
        omokWritableDb.delete(OmokContract.TABLE_NAME, null, null)
    }

    private fun getAllStonesSearchCursor(): Cursor {
        return omokWritableDb.rawQuery("SELECT * FROM ${OmokContract.TABLE_NAME}", null)
    }

    private fun getAllStonesInDatabase(): Stones {
        val cursor = getAllStonesSearchCursor()
        var stones = Stones(listOf())
        while (cursor.moveToNext()) {
            val color =
                cursor.getString(cursor.getColumnIndexOrThrow(OmokContract.TABLE_COLUMN_COLOR))
            val x = cursor.getInt(cursor.getColumnIndexOrThrow(OmokContract.TABLE_COLUMN_X))
            val y = cursor.getInt(cursor.getColumnIndexOrThrow(OmokContract.TABLE_COLUMN_Y))
            stones = stones.addStone(Stone(stringToColorInDb(color), Position(x, y)))
        }
        return stones
    }

    private fun getStoneContentValues(stone: Stone): ContentValues {
        val values = ContentValues()
        values.put("color", colorToStringInDb(stone.color))
        values.put("x", stone.position.x)
        values.put("y", stone.position.y)
        return values
    }

    private fun colorToStringInDb(color: Color): String {
        return when (color) {
            Color.BLACK -> BLACK_STONE_COLOR
            Color.WHITE -> WHITE_STONE_COLOR
        }
    }

    private fun stringToColorInDb(message: String): Color {
        return when (message) {
            BLACK_STONE_COLOR -> Color.BLACK
            WHITE_STONE_COLOR -> Color.WHITE
            else -> throw IllegalArgumentException("잘못된 색")
        }
    }

    companion object {
        const val BLACK_STONE_COLOR = "흑돌"
        const val WHITE_STONE_COLOR = "백돌"
    }
}
