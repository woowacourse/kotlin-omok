package woowacourse.omok.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class OmokHistoryDbHelper(
    context: Context,
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int,
    ) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "OmokPlayHistory.db"

        const val TABLE_NAME = "omok_play_history"
        const val COLUMN_NAME_TURN = "turn"
        const val COLUMN_NAME_POSITION_ROW = "position_row"
        const val COLUMN_NAME_POSITION_COLUMN = "position_column"
        private const val COLUMN_NAME_ID = BaseColumns._ID

        private const val SQL_CREATE_ENTRIES =
            "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_NAME_ID INTEGER PRIMARY KEY," +
                "$COLUMN_NAME_TURN TEXT," +
                "$COLUMN_NAME_POSITION_ROW INTEGER," +
                "$COLUMN_NAME_POSITION_COLUMN INTEGER)"

        const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"
    }
}
