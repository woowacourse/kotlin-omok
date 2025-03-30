package woowacourse.omok.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class FakeOmokDatabaseHelper(context: Context) : SQLiteOpenHelper(
    context,
    null,
    null,
    1,
) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(OmokContract.GameState.CREATE_TABLE)
        db.execSQL(OmokContract.GameInfo.CREATE_TABLE)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int,
    ) {
        db.execSQL("DROP TABLE IF EXISTS ${OmokContract.GameState.TABLE_NAME}")
        db.execSQL("DROP TABLE IF EXISTS ${OmokContract.GameInfo.TABLE_NAME}")
        onCreate(db)
    }
}
