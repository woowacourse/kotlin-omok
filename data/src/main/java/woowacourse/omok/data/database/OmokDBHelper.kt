package woowacourse.omok.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class OmokDBHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_POINTS_TABLE_QUERY)
        db?.execSQL(CREATE_PLAYERS_TABLE_QUERY)
        db?.execSQL(CREATE_GAMES_TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DROP_POINTS_TABLE_QUERY)
        db?.execSQL(DROP_PLAYERS_TABLE_QUERY)
        db?.execSQL(DROP_GAMES_TABLE_QUERY)
    }
}