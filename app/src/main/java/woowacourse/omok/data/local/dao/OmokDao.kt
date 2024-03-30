package woowacourse.omok.data.local.dao

import android.content.Context
import androidx.core.content.contentValuesOf
import woowacourse.omok.data.local.contract.OmokContract.COORDINATE_X
import woowacourse.omok.data.local.contract.OmokContract.COORDINATE_Y
import woowacourse.omok.data.local.contract.OmokContract.ID
import woowacourse.omok.data.local.contract.OmokContract.TABLE_NAME
import woowacourse.omok.data.local.db.OmokDbHelper
import woowacourse.omok.data.local.entity.OmokEntity

class OmokDao(private val context: Context) {
    private val dbHelper = OmokDbHelper(context)

    fun save(omokEntity: OmokEntity): OmokEntity {
        val db = dbHelper.writableDatabase
        val id =
            db.insert(
                TABLE_NAME,
                null,
                contentValuesOf(
                    COORDINATE_X to omokEntity.x,
                    COORDINATE_Y to omokEntity.y,
                ),
            )
        db.close()
        return omokEntity.copy(id = id)
    }

    fun findAll(): List<OmokEntity> {
        val db = dbHelper.readableDatabase
        val cursor =
            db.rawQuery(
                "SELECT $ID, ${COORDINATE_X},${COORDINATE_Y} FROM omok",
                null,
            )

        val entries = mutableListOf<OmokEntity>()

        while (cursor.moveToNext()) {
            val id = cursor.getLong(cursor.getColumnIndexOrThrow(ID))
            val x = cursor.getInt(cursor.getColumnIndexOrThrow(COORDINATE_X))
            val y = cursor.getInt(cursor.getColumnIndexOrThrow(COORDINATE_Y))
            entries.add(OmokEntity(id = id, x = x, y = y))
        }

        cursor.close()
        db.close()
        return entries
    }

    fun drop() {
        val db = dbHelper.writableDatabase
        db.delete(TABLE_NAME, null, null)
        db.close()
    }
}
