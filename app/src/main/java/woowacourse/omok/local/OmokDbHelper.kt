package woowacourse.omok.local

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class OmokDbHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val sql: String =
            "CREATE TABLE IF NOT EXISTS $TABLE_NAME (\n" +
                "$ROW_COMMA string, \n" +
                "$COLUMN_COMMA string\n" +
                ");"

        db.execSQL(sql)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int,
    ) {
        val sql = "DROP TABLE IF EXISTS $TABLE_NAME"

        db.execSQL(sql)
        onCreate(db)
    }

    fun insertOmok(omok: Omok) {
        val values = ContentValues()
        values.put(ROW_COMMA, omok.rowComma)
        values.put(COLUMN_COMMA, omok.columnComma)

        val wd = writableDatabase
        wd.insert(TABLE_NAME, null, values)
        wd.close()
    }

    fun selectOmok(): List<Omok> {
        val list = mutableListOf<Omok>()
        val select = "select * from $TABLE_NAME"
        val rd = readableDatabase
        val cursor = rd.rawQuery(select, null)

        while (cursor.moveToNext()) {
            val rwoComma = cursor.getString(cursor.getColumnIndexOrThrow(ROW_COMMA))
            val columnComma = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COMMA))

            list.add(Omok(rwoComma, columnComma))
        }

        cursor.close()
        rd.close()
        return list
    }

    fun deleteOmok(omok: Omok) {
        val db = writableDatabase
        val whereClause = "$ROW_COMMA = ? AND $COLUMN_COMMA = ?"
        val whereArgs = arrayOf(omok.rowComma, omok.columnComma)

        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()
    }

    fun deleteAllOmok() {
        val omoks = selectOmok()
        omoks.forEach { omok ->
            deleteOmok(omok)
        }
    }

    companion object {
        const val DATABASE_VERSION = 8
        const val DATABASE_NAME = "omok.db"

        const val TABLE_NAME = "OMOK"
        const val ROW_COMMA = "ROW_COMMA"
        const val COLUMN_COMMA = "COLUMN_COMMA"
    }
}
