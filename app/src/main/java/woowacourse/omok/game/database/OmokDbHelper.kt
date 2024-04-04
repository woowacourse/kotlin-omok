package woowacourse.omok.game.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class OmokDbHelper(context: Context) : SQLiteOpenHelper(
    context,
    "omok.db",
    null,
    1,
) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_QUERY)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        old: Int,
        new: Int,
    ) {
        db.execSQL(DROP_QUERY)
        onCreate(db)
    }

    companion object {
        const val CREATE_QUERY =
            "CREATE TABLE IF NOT EXISTS ${OmokContract.TABLE_NAME} (" +
                "${OmokContract.INDEX} INTEGER PRIMARY KEY NOT NULL," +
                "${OmokContract.COLOR} TEXT NOT NULL)"

        const val DROP_QUERY = "DROP TABLE IF EXISTS ${OmokContract.TABLE_NAME}"
    }
}
