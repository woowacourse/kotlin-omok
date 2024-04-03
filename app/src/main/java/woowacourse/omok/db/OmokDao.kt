package woowacourse.omok.db

import android.content.ContentValues
import android.content.Context
import woowacourse.omok.db.OmokContract.POINT_X
import woowacourse.omok.db.OmokContract.POINT_Y
import woowacourse.omok.db.OmokContract.STONE_TYPE_NAME
import woowacourse.omok.db.OmokContract.TABLE_NAME

class OmokDao(context: Context) {
    private val omokDbHelper = OmokDbHelper(context)

    fun insertStone(omokEntity: OmokEntity) {
        val db = omokDbHelper.writableDatabase
        val values =
            ContentValues().apply {
                put(STONE_TYPE_NAME, omokEntity.stoneTypeName)
                put(POINT_X, omokEntity.pointX)
                put(POINT_Y, omokEntity.pointY)
            }
        db.insert(TABLE_NAME, null, values)
    }

    fun deleteAllStones() {
        val db = omokDbHelper.writableDatabase
        db.delete(TABLE_NAME, null, null)
        db.close()
    }

    fun findAllStones(): List<OmokEntity> {
        val stones = mutableListOf<OmokEntity>()
        val db = omokDbHelper.readableDatabase
        val sql = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(sql, null)
        while (cursor.moveToNext()) {
            val stoneType = cursor.getString(cursor.getColumnIndexOrThrow(STONE_TYPE_NAME))
            val pointX = cursor.getInt(cursor.getColumnIndexOrThrow(POINT_X))
            val ponitY = cursor.getInt(cursor.getColumnIndexOrThrow(POINT_Y))

            stones.add(OmokEntity(stoneType, pointX, ponitY))
        }
        cursor.close()
        db.close()
        return stones
    }
}
