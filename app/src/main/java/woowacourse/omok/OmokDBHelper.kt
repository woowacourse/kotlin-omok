package woowacourse.omok

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class OmokDBHelper(context: Context?) : SQLiteOpenHelper(context, "Omok.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val sql = "CREATE TABLE board (indexX int, indexY int, stone varchar(10));"
        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val sql = "DROP TABLE if exists board"
        db?.execSQL(sql)
        onCreate(db)
    }
}
