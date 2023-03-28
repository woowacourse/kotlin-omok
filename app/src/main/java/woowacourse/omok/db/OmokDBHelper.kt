package woowacourse.omok.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class OmokDBHelper(context: Context) : SQLiteOpenHelper(context, "omok.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val sql = "CREATE TABLE if not exists ${OmokContract.TABLE_NAME}(" +
            "id integer primary key autoincrement," +
            "${OmokContract.TABLE_COLUMN_X} integer," +
            "${OmokContract.TABLE_COLUMN_Y} integer," +
            "${OmokContract.TABLE_COLUMN_STONE_COLOR} integer);"
        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val sql = "DROP TABLE if exists ${OmokContract.TABLE_NAME};"
        db?.execSQL(sql)
        onCreate(db)
    }
}
