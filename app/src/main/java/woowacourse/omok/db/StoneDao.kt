package woowacourse.omok.db

import android.content.Context
import androidx.core.content.contentValuesOf
import omok.model.entity.Point
import omok.model.entity.Stone
import omok.model.entity.StoneColor

class StoneDao(
    context: Context,
    private val dbHelper: OmokDbHelper = OmokDbHelper(context),
) {
    fun save(stone: Stone) {
        val db = dbHelper.writableDatabase
        db.insert(
            StoneContract.StoneEntry.TABLE_NAME,
            null,
            contentValuesOf(
                StoneContract.StoneEntry.COLUMN_NAME_X to stone.point.x,
                StoneContract.StoneEntry.COLUMN_NAME_Y to stone.point.y,
                StoneContract.StoneEntry.COLUMN_NAME_STONECOLOR to stone.stoneColor.toDbString(),
            ),
        )
    }

    fun findAt(point: Point): Stone? {
        val db = dbHelper.writableDatabase
        val projection =
            arrayOf(
                StoneContract.StoneEntry.COLUMN_NAME_X,
                StoneContract.StoneEntry.COLUMN_NAME_Y,
                StoneContract.StoneEntry.COLUMN_NAME_STONECOLOR,
            )
        val selection =
            "${StoneContract.StoneEntry.COLUMN_NAME_X} = ${point.x} AND ${StoneContract.StoneEntry.COLUMN_NAME_Y} = ${point.y}"
        val cursor =
            db.query(
                StoneContract.StoneEntry.TABLE_NAME,
                projection,
                selection,
                null,
                null,
                null,
                null,
            )
        while (cursor.moveToNext()) {
            val colorString =
                cursor.getString(cursor.getColumnIndexOrThrow(StoneContract.StoneEntry.COLUMN_NAME_STONECOLOR))
            cursor.close()
            val color = dbStringToStoneColor(colorString)
            return Stone(point, color)
        }
        cursor.close()
        return null
    }

    fun findAll(): Set<Stone> {
        val db = dbHelper.writableDatabase
        val cursor =
            db.query(
                StoneContract.StoneEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null,
            )
        val stones = mutableSetOf<Stone>()
        while (cursor.moveToNext()) {
            val x =
                cursor.getInt(cursor.getColumnIndexOrThrow(StoneContract.StoneEntry.COLUMN_NAME_X))
            val y =
                cursor.getInt(cursor.getColumnIndexOrThrow(StoneContract.StoneEntry.COLUMN_NAME_Y))
            val colorString =
                cursor.getString(cursor.getColumnIndexOrThrow(StoneContract.StoneEntry.COLUMN_NAME_STONECOLOR))
            val color = dbStringToStoneColor(colorString)
            val stone = Stone(Point(x, y), color)
            stones.add(stone)
        }
        cursor.close()
        return stones
    }

    fun deleteAll() {
        val db = dbHelper.writableDatabase
        db.delete(StoneContract.StoneEntry.TABLE_NAME, null, null)
    }

    private fun dbStringToStoneColor(string: String) =
        when (string) {
            STONE_COLOR_WHITE_STRING -> StoneColor.WHITE
            else -> StoneColor.BLACK
        }

    private fun StoneColor.toDbString(): String {
        return when (this) {
            StoneColor.WHITE -> STONE_COLOR_WHITE_STRING
            StoneColor.BLACK -> STONE_COLOR_BLACK_STRING
        }
    }

    companion object {
        private const val STONE_COLOR_WHITE_STRING = "WHITE"
        private const val STONE_COLOR_BLACK_STRING = "BLACK"
    }
}
