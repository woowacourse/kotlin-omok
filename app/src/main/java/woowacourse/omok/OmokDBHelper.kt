package woowacourse.omok

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class OmokDBHelper(context: Context?) : SQLiteOpenHelper(context, "Omok.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val sql = "CREATE TABLE ${OmokContract.TABLE_NAME} (${OmokContract.POSITION_X} int, ${OmokContract.POSITION_Y} int, ${OmokContract.TURN} varchar(10));"
        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val sql = "DROP TABLE if exists ${OmokContract.TABLE_NAME}"
        db?.execSQL(sql)
        onCreate(db)
    }
}
