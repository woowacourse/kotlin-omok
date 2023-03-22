package woowacourse.omok

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BoardDBHelper(context: Context?) : SQLiteOpenHelper(context, BoardContract.DATABASE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
    }
}
