package woowacourse.omok.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.omok.data.OmokContract.Board.CREATE_BOARD_TABLE
import woowacourse.omok.data.OmokContract.Board.DELETE_BOARD_TABLE
import woowacourse.omok.data.OmokContract.Room.CREATE_ROOM_TABLE
import woowacourse.omok.data.OmokContract.Room.DELETE_ROOM_TABLE
import woowacourse.omok.data.OmokContract.User.CREATE_USER_TABLE
import woowacourse.omok.data.OmokContract.User.DELETE_USER_TABLE

class OmokDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("PRAGMA foreign_keys=ON;")
        db.execSQL(CREATE_BOARD_TABLE)
        db.execSQL(CREATE_ROOM_TABLE)
        db.execSQL(CREATE_USER_TABLE)
    }

    override fun onOpen(db: SQLiteDatabase?) {
        super.onOpen(db)
        db?.execSQL("PRAGMA foreign_keys=ON")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DELETE_BOARD_TABLE)
        db.execSQL(DELETE_USER_TABLE)
        db.execSQL(DELETE_ROOM_TABLE)
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "vix database"
    }
}
