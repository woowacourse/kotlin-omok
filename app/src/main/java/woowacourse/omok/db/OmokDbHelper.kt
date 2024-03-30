package woowacourse.omok.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class OmokDbHelper(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION,
) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_QUERY)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int,
    ) {
        db.execSQL(DROP_QUERY)
        onCreate(db)
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Omok.db"
        const val CREATE_QUERY = """
        CREATE TABLE IF NOT EXISTS ${OmokContract.TABLE_NAME} (
            ${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, 
            ${OmokContract.X} TEXT, 
            ${OmokContract.Y} TEXT, 
            ${OmokContract.COLOR} TEXT)
            """
        const val DROP_QUERY = "DROP TABLE IF EXISTS ${OmokContract.TABLE_NAME}"
    }
}
