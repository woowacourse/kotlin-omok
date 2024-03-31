package woowacourse.omok.model.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class OmokDbHelper(context: Context) : SQLiteOpenHelper(context, "omok.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE ${OmokContract.TABLE_NAME} (\n" +
                " ${OmokContract.COLUMN_ROW} int,\n" +
                " ${OmokContract.COLUMN_COL} int,\n" +
                " ${OmokContract.COLUMN_STONE} varchar(50),\n" +
                " ${OmokContract.COLUMN_ID} int\n" +
                ")",
        )
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int,
    ) {
        TODO("Not yet implemented")
    }
}
