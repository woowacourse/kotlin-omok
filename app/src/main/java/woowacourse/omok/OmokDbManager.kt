package woowacourse.omok

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import domain.Board
import domain.Color
import domain.OmokGame
import domain.Position
import domain.RenjuRuleAdapter
import domain.Stone
import domain.Stones

class OmokDbManager(omokDbHelper: OmokDbHelper) {

    private val omokWritableDb: SQLiteDatabase = omokDbHelper.writableDatabase

    fun getOmokGame(): OmokGame {
        val stones = getAllStonesInDatabase()
        return OmokGame(Board(stones, RenjuRuleAdapter()))
    }

    fun updateOmokDatabase(stone: Stone?) {
        if (stone != null) {
            val contentValues = getStoneContentValues(stone)
            omokWritableDb.insert(OmokContract.TABLE_NAME, null, contentValues)
        }
    }

    fun deleteOmokDatabase() {
        omokWritableDb.delete(OmokContract.TABLE_NAME, null, null)
    }

    private fun getAllStonesInDatabase(): Stones {
        var stones = Stones(listOf())
        val cursor = omokWritableDb.rawQuery("SELECT * FROM ${OmokContract.TABLE_NAME}", null)
        while (cursor.moveToNext()) {
            val color =
                cursor.getString(cursor.getColumnIndexOrThrow(OmokContract.TABLE_COLUMN_COLOR))
            val x = cursor.getInt(cursor.getColumnIndexOrThrow(OmokContract.TABLE_COLUMN_X))
            val y = cursor.getInt(cursor.getColumnIndexOrThrow(OmokContract.TABLE_COLUMN_Y))
            stones = stones.addStone(Stone(Converter.stringToColor(color), Position(x, y)))
        }
        return stones
    }

    private fun getStoneContentValues(stone: Stone): ContentValues {
        val values = ContentValues()
        values.put("color", Converter.colorToString(stone.color))
        values.put("x", stone.position.x)
        values.put("y", stone.position.y)
        return values
    }
}
