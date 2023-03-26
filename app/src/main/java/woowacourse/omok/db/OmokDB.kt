package woowacourse.omok.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import omok.model.stone.Coordinate
import omok.model.stone.GoStone
import omok.model.stone.GoStoneColor
import woowacourse.omok.db.OmokConstant.STONE_COLOR_BLACK
import woowacourse.omok.db.OmokConstant.STONE_COLOR_WHITE
import woowacourse.omok.db.OmokConstant.TABLE_COLUMN_COLOR
import woowacourse.omok.db.OmokConstant.TABLE_COLUMN_X
import woowacourse.omok.db.OmokConstant.TABLE_COLUMN_Y

class OmokDB(
    private val context: Context
) {
    private val db: SQLiteDatabase by lazy {
        OmokDBHelper(context).writableDatabase
    }
    private val repository = OmokRepository(db)

    fun insert(goStone: GoStone?) {
        if (goStone == null) return

        val value = ContentValues().apply {
            put(TABLE_COLUMN_X, goStone.coordinate.x)
            put(TABLE_COLUMN_Y, goStone.coordinate.y)
            when (goStone.color) {
                GoStoneColor.BLACK -> put(TABLE_COLUMN_COLOR, STONE_COLOR_BLACK)
                GoStoneColor.WHITE -> put(TABLE_COLUMN_COLOR, STONE_COLOR_WHITE)
            }
        }
        repository.insert(value)
    }

    fun clear() {
        repository.clear()
    }

    fun getExistingStones(): List<GoStone> {
        val stones = mutableListOf<GoStone>()
        val cursor = repository.cursor

        while (cursor.moveToNext()) {
            val xCoordinate = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_COLUMN_X))
            val yCoordinate = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_COLUMN_Y))
            val color = when (cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_COLUMN_COLOR))) {
                STONE_COLOR_BLACK -> GoStoneColor.BLACK
                STONE_COLOR_WHITE -> GoStoneColor.WHITE
                else -> GoStoneColor.BLACK
            }

            stones.add(GoStone(color, Coordinate(xCoordinate, yCoordinate)))
        }

        return stones
    }
}