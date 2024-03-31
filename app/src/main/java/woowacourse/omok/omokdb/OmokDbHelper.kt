package woowacourse.omok.omokdb

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class OmokDbHelper(context: Context) : SQLiteOpenHelper(context, "omok.db", null, 3) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE ${OmokContract.TABLE_NAME} (\n" +
                "  ${OmokContract.COLUMN_ROW} char(1),\n" +
                "  ${OmokContract.COLUMN_COLUMN} int,\n" +
                "  ${OmokContract.COLUMN_COLOR} char(1),\n" +
                "  ${BaseColumns._ID} integer primary key\n" +
                ")",
        )
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int,
    ) {
        db?.execSQL("DROP TABLE IF EXISTS ${OmokContract.TABLE_NAME}")
        onCreate(db)
    }
}
