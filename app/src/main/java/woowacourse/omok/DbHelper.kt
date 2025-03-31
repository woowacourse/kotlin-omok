package woowacourse.omok

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_VERSION = 5
        const val DATABASE_NAME = "Room.db"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(RoomContract.SQL_CREATE_NICKNAMES)
        db.execSQL(RoomContract.SQL_CREATE_ROOMS)
        db.execSQL(RoomContract.SQL_CREATE_STONES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(RoomContract.SQL_DELETE_STONES)
        db.execSQL(RoomContract.SQL_DELETE_ROOMS)
        db.execSQL(RoomContract.SQL_DELETE_NICKNAMES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }
}
