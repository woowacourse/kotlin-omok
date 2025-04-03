package woowacourse.omok.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.omok.data.GameContract.SQL_CREATE_GAMES
import woowacourse.omok.data.GameContract.SQL_DELETE_GAMES
import woowacourse.omok.data.StoneContract.SQL_CREATE_STONES
import woowacourse.omok.data.StoneContract.SQL_DELETE_STONES

class OmokDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, INIT_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            SQL_CREATE_STONES,
        )
        db.execSQL(
            SQL_CREATE_GAMES,
        )
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int,
    ) {
        db.execSQL(SQL_DELETE_STONES)
        db.execSQL(SQL_DELETE_GAMES)
        onCreate(db)
    }

    override fun onDowngrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int,
    ) {
        onUpgrade(db, oldVersion, newVersion)
    }

    companion object {
        private const val DB_NAME = "omok_db"
        private const val INIT_VERSION = 1
    }
}
