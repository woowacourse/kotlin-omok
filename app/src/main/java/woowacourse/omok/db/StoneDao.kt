package woowacourse.omok.db

import android.content.Context
import android.provider.BaseColumns
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
                StoneContract.StoneEntry.COLUMN_NAME_STONECOLOR to stone.stoneColor.ordinal,
            ),
        )
    }

    fun findAt(point: Point): Stone? {
        val db = dbHelper.writableDatabase
        val projection =
            arrayOf(
                BaseColumns._ID,
                StoneContract.StoneEntry.COLUMN_NAME_X,
                StoneContract.StoneEntry.COLUMN_NAME_Y,
                StoneContract.StoneEntry.COLUMN_NAME_STONECOLOR,
            )
        val selection = "${StoneContract.StoneEntry.COLUMN_NAME_X} = ${point.x} AND ${StoneContract.StoneEntry.COLUMN_NAME_Y} = ${point.y}"
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
            val colorOrdinal = cursor.getInt(cursor.getColumnIndexOrThrow(StoneContract.StoneEntry.COLUMN_NAME_STONECOLOR))
            cursor.close()
            val color = ordinalToStoneColor(colorOrdinal)
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
            val x = cursor.getInt(cursor.getColumnIndexOrThrow(StoneContract.StoneEntry.COLUMN_NAME_X))
            val y = cursor.getInt(cursor.getColumnIndexOrThrow(StoneContract.StoneEntry.COLUMN_NAME_Y))
            val colorOrdinal = cursor.getInt(cursor.getColumnIndexOrThrow(StoneContract.StoneEntry.COLUMN_NAME_STONECOLOR))
            val color = ordinalToStoneColor(colorOrdinal)
            val stone = Stone(Point(x, y), color)
            stones.add(stone)
        }
        cursor.close()
        return stones
    }

    fun drop() {
        val db = dbHelper.writableDatabase
        db.delete(StoneContract.StoneEntry.TABLE_NAME, null, null)
    }

    private fun ordinalToStoneColor(ordinal: Int) =
        when (ordinal) {
            StoneColor.WHITE.ordinal -> StoneColor.WHITE
            else -> StoneColor.BLACK
        }
}
