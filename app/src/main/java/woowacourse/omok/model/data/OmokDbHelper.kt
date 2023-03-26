package woowacourse.omok.model.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.omok.model.data.OmokContract.TABLE_COLUMN_INDEX
import woowacourse.omok.model.data.OmokContract.TABLE_COLUMN_STONE_COLOR
import woowacourse.omok.model.data.OmokContract.TABLE_COLUMN_STONE_COLOR_LENGTH
import woowacourse.omok.model.data.OmokContract.TABLE_NAME

class OmokDbHelper(context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE $TABLE_NAME(" +
                "$TABLE_COLUMN_INDEX int," +
                "$TABLE_COLUMN_STONE_COLOR varchar($TABLE_COLUMN_STONE_COLOR_LENGTH)" +
                ");"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    companion object {
        private const val DB_NAME = "omok.db"
        private const val DB_VERSION = 2
    }
}
