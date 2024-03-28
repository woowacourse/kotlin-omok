package woowacourse.omok.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class OmokDbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE ${OmokContract.TABLE_NAME} (\n" +
                "  ${OmokContract.STONE_TYPE} int,\n" +
                "  ${OmokContract.POINT_X} int,\n" +
                "  ${OmokContract.POINT_Y} int\n" +
                ")",
        )
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int,
    ) {
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Omok.db"
    }
}
