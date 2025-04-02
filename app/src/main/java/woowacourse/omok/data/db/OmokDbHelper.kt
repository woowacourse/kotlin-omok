package woowacourse.omok.data.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class OmokDbHelper(
    context: Context,
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Omok.db"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(OmokContract.SQL_CREATE_GAME_STATE)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int,
    ) {
        db.execSQL(OmokContract.SQL_DELETE_GAME_STATE)
        onCreate(db)
    }

    override fun onDowngrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int,
    ) {
        onUpgrade(db, oldVersion, newVersion)
    }

    fun insertGameState(values: ContentValues) {
        writableDatabase.insert(OmokContract.TABLE_GAME_STATE, null, values)
    }

    fun deleteGameState(gameId: Int) {
        writableDatabase.delete(
            OmokContract.TABLE_GAME_STATE,
            "${OmokContract.COLUMN_GAME_ID}=?",
            arrayOf(gameId.toString()),
        )
    }

    fun queryGameState(gameId: Int): Cursor =
        readableDatabase.query(
            OmokContract.TABLE_GAME_STATE,
            arrayOf(
                OmokContract.COLUMN_POSITION_ROW,
                OmokContract.COLUMN_POSITION_COL,
                OmokContract.COLUMN_POSITION_STATE,
                OmokContract.COLUMN_LAST_TURN,
            ),
            "${OmokContract.COLUMN_GAME_ID}=?",
            arrayOf(gameId.toString()),
            null,
            null,
            null,
        )
}
