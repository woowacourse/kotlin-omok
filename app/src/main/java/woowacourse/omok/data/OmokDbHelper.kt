package woowacourse.omok.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class OmokDbHelper(context: Context) : SQLiteOpenHelper(context, "omok.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE ${OmokContract.TABLE_NAME} (\n" +
                " ${OmokContract.COLUMN_ID} integer primary key autoincrement,\n" +
                " ${OmokContract.COLUMN_ROW} integer,\n" +
                " ${OmokContract.COLUMN_COL} integer,\n" +
                " ${OmokContract.COLUMN_STONE} varchar(50)\n" +
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
