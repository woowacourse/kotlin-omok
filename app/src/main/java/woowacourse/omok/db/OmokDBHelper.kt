package woowacourse.omok.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class OmokDBHelper(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE ${OmokConstract.TABLE_NAME} (" +
                    "  ${OmokConstract.TABLE_COLUMN_POSITION} int not null," +
                    "  ${OmokConstract.TABLE_COLUMN_IS_BLACK} int" +
                    ");"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${OmokConstract.TABLE_NAME}")
        onCreate(db)
    }

    companion object {
        private val DATABASE_NAME = "omok"
    }
}