package woowacourse.omok.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.omok.db.OmokContract.DATABASE_NAME
import woowacourse.omok.db.OmokContract.DATABASE_VERSION
import woowacourse.omok.db.OmokContract.POINT_X
import woowacourse.omok.db.OmokContract.POINT_Y
import woowacourse.omok.db.OmokContract.STONE_TYPE
import woowacourse.omok.db.OmokContract.TABLE_NAME

class OmokDbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val sql: String =
            "CREATE TABLE IF NOT EXISTS $TABLE_NAME (\n" +
                "  $STONE_TYPE text,\n" +
                "  $POINT_X int,\n" +
                "  $POINT_Y int\n" +
                ")"

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
