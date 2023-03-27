package woowacourse.omok.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase

object OmokDatabase {
    fun getDatabase(context: Context): SQLiteDatabase {
        val dbHelper = OmokDBHelper(context)
        return dbHelper.writableDatabase
    }
}
