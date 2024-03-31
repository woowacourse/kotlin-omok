package woowacourse.omok.local.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.omok.local.db.OmokContract.DATABASE_NAME
import woowacourse.omok.local.db.OmokContract.DATABASE_VERSION

class OmokDbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS ${OmokContract.TABLE_NAME} (\n" +
                    " ${OmokContract.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    " ${OmokContract.COORDINATE_X} INTEGER,\n" +
                    " ${OmokContract.COORDINATE_Y} INTEGER,\n" +
                    " ${OmokContract.POSITION_TYPE} VARCHAR(10)\n" +
                    ")",
        )
    }
    
    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int,
    ) {
        db.execSQL("DROP TABLE IF EXISTS ${OmokContract.TABLE_NAME}")
        onCreate(db)
    }
}
