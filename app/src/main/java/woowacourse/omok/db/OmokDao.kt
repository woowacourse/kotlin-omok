package woowacourse.omok.db

import android.content.Context
import androidx.core.content.contentValuesOf

class OmokDao(context: Context) {
    private val dbHelper = OmokDbHelper(context = context)

    fun insertOmok(entry: OmokEntity): OmokEntity {
        val wd = dbHelper.writableDatabase
        wd.insert(
            OmokContract.TABLE_NAME,
            null,
            contentValuesOf(
                OmokContract.COLUMN_STONE_COLOR to entry.stoneColor,
                OmokContract.COLUMN_POSITION_X to entry.positionX,
                OmokContract.COLUMN_POSITION_Y to entry.positionY,
            ),
        )
        wd.close()
        return entry
    }

    fun findAllOmok(): List<OmokEntity> {
        val rd = dbHelper.readableDatabase
        val cursor =
            rd.query(
                OmokContract.TABLE_NAME,
                arrayOf(
                    OmokContract.COLUMN_STONE_COLOR,
                    OmokContract.COLUMN_POSITION_X,
                    OmokContract.COLUMN_POSITION_Y,
                ),
                null,
                null,
                null,
                null,
                null,
            )

        val entries = mutableListOf<OmokEntity>()

        while (cursor.moveToNext()) {
            val color =
                cursor.getString(cursor.getColumnIndexOrThrow(OmokContract.COLUMN_STONE_COLOR))
            val positionX =
                cursor.getInt(cursor.getColumnIndexOrThrow(OmokContract.COLUMN_POSITION_X))
            val positionY =
                cursor.getInt(cursor.getColumnIndexOrThrow(OmokContract.COLUMN_POSITION_Y))
            entries.add(OmokEntity(color, positionX, positionY))
        }

        cursor.close()
        rd.close()
        return entries
    }

    fun resetAll() {
        val wd = dbHelper.writableDatabase
        wd.delete(OmokContract.TABLE_NAME, null, null)
        wd.close()
    }
}
