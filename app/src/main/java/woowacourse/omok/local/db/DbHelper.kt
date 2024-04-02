package woowacourse.omok.local.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.omok.local.db.OmokDbContract.COLUMN_COMMA
import woowacourse.omok.local.db.OmokDbContract.DATABASE_NAME
import woowacourse.omok.local.db.OmokDbContract.DATABASE_VERSION
import woowacourse.omok.local.db.OmokDbContract.ROW_COMMA
import woowacourse.omok.local.db.OmokDbContract.TABLE_NAME

class DbHelper(context: Context?) :
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
}
