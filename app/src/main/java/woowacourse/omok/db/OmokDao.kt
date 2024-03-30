package woowacourse.omok.db

import android.content.ContentValues
import android.content.Context
import woowacourse.omok.domain.model.Stone

class OmokDao(context: Context) {
    private val omokDbHelper = OmokDbHelper(context)

    fun insertStone(stone: Stone) {
        val db = omokDbHelper.writableDatabase
        val values =
            ContentValues().apply {
                put(OmokContract.STONE_TYPE, stone.type.name)
                put(OmokContract.POINT_X, stone.point.x)
                put(OmokContract.POINT_Y, stone.point.y)
            }
        db.insert(OmokContract.TABLE_NAME, null, values)
    }

    fun deleteAllStones() {
        val db = omokDbHelper.writableDatabase
        db.delete(OmokContract.TABLE_NAME, null, null)
        db.close()
    }
}
