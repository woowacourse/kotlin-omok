package woowacourse.omok

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class OmokDBHelper(
    context: Context?
) : SQLiteOpenHelper(
    context,
    DB_NAME,
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE ${OmokContract.TABLE_NAME}(" +
                "placedIndex int," +
                "color varchar(10)" +
                ");"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${OmokContract.TABLE_NAME}")
        onCreate(db)
    }

    companion object {
        private const val DB_NAME = "woogi.db"
    }
}
