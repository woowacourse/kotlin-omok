package woowacourse.omok.local.db

import android.content.ContentValues
import android.content.Context

class OmokDao(context: Context) {
    private val db = DbHelper(context = context)

    fun insertOmok(omokEntity: OmokEntity) {
        val values = ContentValues()
        values.put(OmokDbContract.ROW_COMMA, omokEntity.rowComma)
        values.put(OmokDbContract.COLUMN_COMMA, omokEntity.columnComma)

        val wd = db.writableDatabase
        wd.insert(OmokDbContract.TABLE_NAME, null, values)
        wd.close()
    }

    fun selectOmok(): List<OmokEntity> {
        val list = mutableListOf<OmokEntity>()
        val select = "select * from ${OmokDbContract.TABLE_NAME}"
        val rd = db.readableDatabase
        val cursor = rd.rawQuery(select, null)

        while (cursor.moveToNext()) {
            val rwoComma = cursor.getString(cursor.getColumnIndexOrThrow(OmokDbContract.ROW_COMMA))
            val columnComma =
                cursor.getString(cursor.getColumnIndexOrThrow(OmokDbContract.COLUMN_COMMA))

            list.add(OmokEntity(rwoComma, columnComma))
        }

        cursor.close()
        rd.close()
        return list
    }

    fun deleteOmok(omokEntity: OmokEntity) {
        val db = db.writableDatabase
        val whereClause = "${OmokDbContract.ROW_COMMA} = ? AND ${OmokDbContract.COLUMN_COMMA} = ?"
        val whereArgs = arrayOf(omokEntity.rowComma, omokEntity.columnComma)

        db.delete(OmokDbContract.TABLE_NAME, whereClause, whereArgs)
        db.close()
    }

    fun deleteAllOmok() {
        val db = db.writableDatabase
        db.delete(OmokDbContract.TABLE_NAME, null, null)
    }
}
