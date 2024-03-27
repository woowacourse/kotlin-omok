package woowacourse.omok

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class OmokDbHelper(context: Context) : SQLiteOpenHelper(context, "omok.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE ${OmokContract.TABLE_NAME} (\n" +
                "  ${OmokContract.COLUMN_INDEX} int,\n" +
                "  ${OmokContract.COLUMN_COLOR} char(1)\n" +
                ")",
        )
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int,
    ) {
    }
}
