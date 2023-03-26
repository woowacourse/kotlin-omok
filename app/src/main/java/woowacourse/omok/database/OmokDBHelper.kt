package woowacourse.omok.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.omok.database.OmokContract.TABLE_COLUMN_ID
import woowacourse.omok.database.OmokContract.TABLE_COLUMN_POSITION_X
import woowacourse.omok.database.OmokContract.TABLE_COLUMN_POSITION_Y
import woowacourse.omok.database.OmokContract.TABLE_COLUMN_STONE_COLOR
import woowacourse.omok.database.OmokContract.TABLE_NAME

class OmokDBHelper(
    context: Context?,
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "lope_db"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        var sql: String = "CREATE TABLE $TABLE_NAME (" +
            "$TABLE_COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "$TABLE_COLUMN_STONE_COLOR INTEGER NOT NULL," +
            "$TABLE_COLUMN_POSITION_X INTEGER NOT NULL," +
            "$TABLE_COLUMN_POSITION_Y INTEGER NOT NULL" +
            ")"

        db!!.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        val sql: String = "DROP TABLE IF EXISTS $TABLE_NAME"

        db!!.execSQL(sql)
        onCreate(db)
    }
}
