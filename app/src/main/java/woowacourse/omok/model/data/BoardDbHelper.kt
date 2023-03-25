package woowacourse.omok.model.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.omok.model.data.BoardContract.NAME
import woowacourse.omok.model.data.BoardContract.SQL_CREATE_ENTRIES
import woowacourse.omok.model.data.BoardContract.SQL_DELETE_ENTRIES
import woowacourse.omok.model.data.BoardContract.VERSION_ONE

class BoardDbHelper(
    context: Context?,
) : SQLiteOpenHelper(context, NAME, null, VERSION_ONE) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }
}
