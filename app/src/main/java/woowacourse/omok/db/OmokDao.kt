package woowacourse.omok.db

import android.content.ContentValues
import android.content.Context
import woowacourse.omok.domain.model.Point
import woowacourse.omok.domain.model.Stone
import woowacourse.omok.domain.model.StoneType

class OmokDao(context: Context) {
    private val omokDb = OmokDbHelper(context).writableDatabase

    fun saveStone(stone: Stone) {
        val newStone =
            ContentValues().apply {
                put(OmokContract.STONE_TYPE, stone.type.value)
                put(OmokContract.POINT_X, stone.point.x)
                put(OmokContract.POINT_Y, stone.point.y)
            }
        omokDb.insert(OmokContract.TABLE_NAME, null, newStone)
    }

    fun resetGame() {
        omokDb.delete(OmokContract.TABLE_NAME, null, null)
    }

    fun getStonesFromDatabase(): List<Stone> {
        val projection =
            arrayOf(OmokContract.STONE_TYPE, OmokContract.POINT_X, OmokContract.POINT_Y)
        val cursor =
            omokDb.query(OmokContract.TABLE_NAME, projection, null, null, null, null, null)

        val result = mutableListOf<Stone>()
        with(cursor) {
            while (moveToNext()) {
                val stoneTypeValue = getInt(cursor.getColumnIndexOrThrow(OmokContract.STONE_TYPE))
                val pointX = getInt(cursor.getColumnIndexOrThrow(OmokContract.POINT_X))
                val pointY = getInt(cursor.getColumnIndexOrThrow(OmokContract.POINT_Y))
                result.add(Stone(StoneType.fromValue(stoneTypeValue), Point(pointX, pointY)))
            }
        }
        cursor.close()
        return result
    }
}
