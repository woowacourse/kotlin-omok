package woowacourse.omok.db

import android.content.ContentValues
import android.content.Context
import woowacourse.omok.db.OmokContract.POINT_X
import woowacourse.omok.db.OmokContract.POINT_Y
import woowacourse.omok.db.OmokContract.STONE_TYPE
import woowacourse.omok.db.OmokContract.TABLE_NAME
import woowacourse.omok.domain.model.Point
import woowacourse.omok.domain.model.Stone
import woowacourse.omok.domain.model.StoneType.Companion.getStoneTypeByName

class OmokDao(context: Context) {
    private val omokDbHelper = OmokDbHelper(context)

    fun insertStone(stone: Stone) {
        val db = omokDbHelper.writableDatabase
        val values =
            ContentValues().apply {
                put(STONE_TYPE, stone.type.name)
                put(POINT_X, stone.point.x)
                put(POINT_Y, stone.point.y)
            }
        db.insert(TABLE_NAME, null, values)
    }

    fun deleteAllStones() {
        val db = omokDbHelper.writableDatabase
        db.delete(TABLE_NAME, null, null)
        db.close()
    }

    fun findAllStones(): List<Stone> {
        val stones = mutableListOf<Stone>()
        val db = omokDbHelper.readableDatabase
        val sql = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(sql, null)
        while (cursor.moveToNext()) {
            val stoneType =
                getStoneTypeByName(cursor.getString(cursor.getColumnIndexOrThrow(STONE_TYPE)))
            val pointX = cursor.getInt(cursor.getColumnIndexOrThrow(POINT_X))
            val ponitY = cursor.getInt(cursor.getColumnIndexOrThrow(POINT_Y))

            stones.add(Stone(stoneType, Point(pointX, ponitY)))
        }
        cursor.close()
        db.close()
        return stones
    }
}
