package woowacourse.omok.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.omok.database.contract.CREATE_POINTS_TABLE_QUERY
import woowacourse.omok.database.contract.DB_NAME
import woowacourse.omok.database.contract.DB_VERSION
import woowacourse.omok.database.contract.DROP_POINTS_TABLE_QUERY

class OmokDBHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_POINTS_TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DROP_POINTS_TABLE_QUERY)
    }
}
