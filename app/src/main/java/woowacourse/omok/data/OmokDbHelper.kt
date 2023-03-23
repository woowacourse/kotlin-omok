package woowacourse.omok.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.omok.data.OmokContract.Board.CREATE_BOARD_TABLE
import woowacourse.omok.data.OmokContract.Board.DELETE_BOARD_TABLE

class OmokDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_BOARD_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DELETE_BOARD_TABLE)
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "vix database"
    }
}
