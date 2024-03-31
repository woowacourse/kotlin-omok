package woowacourse.omok.model.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import woowacourse.omok.model.db.OmokContract.OmokEntry

class OmokDbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_OMOK_TABLE)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int,
    ) {
        db.execSQL(DELETE_OMOK_GAME)
        onCreate(db)
    }

    companion object {
        private const val CREATE_OMOK_TABLE =
            "CREATE TABLE ${OmokEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${OmokEntry.POINT_X} INTEGER," +
                "${OmokEntry.POINT_Y} INTEGER," +
                "${OmokEntry.TURN} TEXT)"
        private const val DELETE_OMOK_GAME =
            "DROP TABLE IF EXISTS ${OmokEntry.TABLE_NAME}"
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "omok.db"
    }
}
