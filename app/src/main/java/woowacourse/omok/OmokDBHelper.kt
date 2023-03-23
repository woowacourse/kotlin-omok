package woowacourse.omok

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class OmokDBHelper(context: Context?) : SQLiteOpenHelper(context, "omok.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE ${OmokContract.TABLE_NAME} (" +
                    "  ${OmokContract.TABLE_COLUMN_X_POINT} int not null," +
                    "  ${OmokContract.TABLE_COLUMN_Y_POINT} int not null," +
                    "  ${OmokContract.TABLE_COLUMN_STONE_COLOR} varchar(255)" +
                    ");"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${OmokContract.TABLE_NAME}")
        onCreate(db)
    }
}