package woowacourse.omok.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class OmokDBHelper(
    context: Context?
) : SQLiteOpenHelper(context, "omok.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE ${OmokConstract.TABLE_NAME} (" +
                "  ${OmokConstract.TABLE_COLUMN_COLOR} varchar(30) not null," +
                "  ${OmokConstract.TABLE_COLUMN_POSITION} int" +
                ");"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${OmokConstract.TABLE_NAME}")
        onCreate(db)
    }
}
