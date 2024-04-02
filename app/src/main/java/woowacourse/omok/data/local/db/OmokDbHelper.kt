package woowacourse.omok.data.local.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.omok.data.local.contract.OmokContract.COORDINATE_X
import woowacourse.omok.data.local.contract.OmokContract.COORDINATE_Y
import woowacourse.omok.data.local.contract.OmokContract.DATABASE_NAME
import woowacourse.omok.data.local.contract.OmokContract.DATABASE_VERSION
import woowacourse.omok.data.local.contract.OmokContract.ID
import woowacourse.omok.data.local.contract.OmokContract.TABLE_NAME

class OmokDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
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

    override fun onDowngrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int,
    ) {
        onUpgrade(db, oldVersion, newVersion)
    }

    companion object {
        private const val SQL_CREATE_ENTRIES =
            "CREATE TABLE $TABLE_NAME (" +
                "$ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COORDINATE_X INTEGER," +
                "$COORDINATE_Y INTEGER)"
        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"
    }
}
