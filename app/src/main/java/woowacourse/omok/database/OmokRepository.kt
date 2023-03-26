package woowacourse.omok.database

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import omok.model.stone.Coordinate
import omok.model.stone.GoStone
import woowacourse.omok.database.OmokDBHelper.Companion.KEY_BOARD_INDEX
import woowacourse.omok.database.OmokDBHelper.Companion.KEY_COORDINATE_X
import woowacourse.omok.database.OmokDBHelper.Companion.KEY_COORDINATE_Y
import woowacourse.omok.database.OmokDBHelper.Companion.KEY_GO_STONE_COLOR
import woowacourse.omok.database.OmokDBHelper.Companion.POINT_TABLE_NAME
import woowacourse.omok.model.GoStoneColorNumber

class OmokRepository(private val database: SQLiteDatabase) : Repository {
    override fun getAll(): Map<GoStone, Int> {
        val stones = mutableMapOf<GoStone, Int>()
        database.rawQuery("SELECT * FROM $POINT_TABLE_NAME", null).use {
            while (it.moveToNext()) {
                val goStoneColorNumber = it.getInt(it.getColumnIndexOrThrow(KEY_GO_STONE_COLOR))
                val index = it.getInt(it.getColumnIndexOrThrow(KEY_BOARD_INDEX))
                val x = it.getInt(it.getColumnIndexOrThrow(KEY_COORDINATE_X))
                val y = it.getInt(it.getColumnIndexOrThrow(KEY_COORDINATE_Y))
                val goStone = GoStone(GoStoneColorNumber.convertGoStoneColor(goStoneColorNumber), Coordinate(x, y))
                stones[goStone] = index
            }
        }
        return stones.toMap()
    }

    override fun insert(goStone: GoStone, index: Int) {
        val record = ContentValues().apply {
            put(KEY_GO_STONE_COLOR, GoStoneColorNumber.convertGoStoneColorNumber(goStone.color).number)
            put(KEY_BOARD_INDEX, index)
            put(KEY_COORDINATE_X, goStone.coordinate.x)
            put(KEY_COORDINATE_Y, goStone.coordinate.y)
        }
        database.insert(POINT_TABLE_NAME, null, record)
    }

    override fun isEmpty(): Boolean =
        database.rawQuery("SELECT * FROM $POINT_TABLE_NAME", null).use { it.count == 0 }

    override fun clear() {
        database.execSQL("DELETE FROM $POINT_TABLE_NAME")
    }

    override fun close() {
        database.close()
    }
}
