package woowacourse.omok.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class OmokDBHelper(context: Context?) : SQLiteOpenHelper(context, "omok.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE ${OmokDBNames.TABLE_NAME} (" +
                    "  ${OmokDBNames.TABLE_COLUMN_X_POINT} int not null," +
                    "  ${OmokDBNames.TABLE_COLUMN_Y_POINT} int not null," +
                    "  ${OmokDBNames.TABLE_COLUMN_STONE_COLOR} varchar(255)" +
                    ");"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${OmokDBNames.TABLE_NAME}")
        onCreate(db)
    }
}